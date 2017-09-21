package com.cherry.winter.yakuzi.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.cherry.winter.yakuzi.message.ApplicationException;
import com.cherry.winter.yakuzi.message.ErrorMessages;
import com.cherry.winter.yakuzi.model.OrderInfo;
import com.cherry.winter.yakuzi.model.ProductInfo;
import com.cherry.winter.yakuzi.model.TicketInfo;
import com.cherry.winter.yakuzi.redis.KeyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Fangwei on 16/5/14.
 */
@Service("ticketService")
public class TicketServiceImp implements TicketService {

  private final long EXPIRED_TICKET_MILLIS = 900_000l;

  @Resource(name = "redisTemplate")
  RedisTemplate redisTemplate;

  @Resource(name = "productService")
  ProductService productService;

  @Resource(name = "orderService")
  OrderService orderService;

  @Override
  public String createTicket(long userId, long productId) {
    ProductInfo productInfo = productService.getProductInfo(productId);
    if (productInfo.getStartAt() > System.currentTimeMillis() || productInfo.getEndAt() < System
        .currentTimeMillis()) {
      throw new ApplicationException(ErrorMessages.PRODUCT_IS_NOT_ON_SALE);
    }
    String ticketId = UUID.randomUUID().toString();
    if (redisTemplate.boundValueOps(KeyUtils.userTicketId(userId, productId))
        .setIfAbsent(ticketId)) {
      TicketInfo ticketInfo = new TicketInfo();
      ticketInfo.setId(ticketId);
      ticketInfo.setUserId(userId);
      ticketInfo.setProductId(productId);
      ticketInfo.setCreateAt(System.currentTimeMillis());
      ticketInfo.setExpireAt(ticketInfo.getCreateAt() + EXPIRED_TICKET_MILLIS);
      saveTicketInfo(ticketInfo);
      redisTemplate.boundValueOps(KeyUtils.userTicketId(userId, productId)).set(ticketId);
      redisTemplate.boundListOps(KeyUtils.productTicketIdList(productId)).rightPush(ticketId);
      return ticketId;
    } else {
      return redisTemplate.boundValueOps(KeyUtils.userTicketId(userId, productId)).get().toString();
    }
  }

  private void saveTicketInfo(TicketInfo ticketInfo) {
    Map<String, Object> map = new JacksonHashMapper(TicketInfo.class).toHash(ticketInfo);
    //ticketId to ticket_info
    redisTemplate.boundHashOps(KeyUtils.ticketInfo(ticketInfo.getId())).putAll(map);
  }

  @Override
  public TicketInfo getTicketInfo(String ticketId) {
    Map<String, Object> map = redisTemplate.boundHashOps(KeyUtils.ticketInfo(ticketId)).entries();
    if (map == null || map.isEmpty()) {
      throw new ApplicationException(ErrorMessages.TICKET_IS_NOT_EXIST);
    }
    return (TicketInfo) new JacksonHashMapper(TicketInfo.class).fromHash(map);
  }

  @Override
  public List<String> getTicketList(long productId, long start, long end) {
    return redisTemplate.boundListOps(KeyUtils.productTicketIdList(productId)).range(start, end);
  }

  private final long PRODUCT_PAGE_COUNT = 1000l;
  private final long TICKET_START = 0l;

  @Scheduled(fixedDelay = 60_000L, initialDelay = 3_000L)
  private void recoverTicket() {
    //
    long start = 0;
    List<ProductInfo> productInfoList = productService
        .getActivityProductInfoList(start * PRODUCT_PAGE_COUNT, (start + 1) * PRODUCT_PAGE_COUNT);
    if (CollectionUtils.isNotEmpty(productInfoList)) {
      for (ProductInfo productInfo : productInfoList) {
        List<String> ticketIdList = getTicketList(productInfo.getProductId(), TICKET_START,
            productInfo.getAmount());
        if (CollectionUtils.isNotEmpty(ticketIdList)) {
          for (String ticketId : ticketIdList) {
            TicketInfo ticketInfo = getTicketInfo(ticketId);
            if (ticketInfo == null) {
              redisTemplate.boundListOps(KeyUtils.productTicketIdList(productInfo.getProductId()))
                  .remove(1l, ticketId);
              redisTemplate.delete(
                  KeyUtils.userTicketId(ticketInfo.getUserId(), productInfo.getProductId()));
            }
            if (ticketInfo.getExpireAt() < System.currentTimeMillis()) {
              OrderInfo orderInfo = orderService.getOrder(ticketId);
              if (orderInfo != null && orderInfo.getExpiredAt() < System.currentTimeMillis()) {
                redisTemplate.boundListOps(KeyUtils.productTicketIdList(productInfo.getProductId()))
                    .remove(1l, ticketId);
                redisTemplate.delete(
                    KeyUtils.userTicketId(ticketInfo.getUserId(), productInfo.getProductId()));
              }
            }
          }
        }
      }
    }
  }
}
