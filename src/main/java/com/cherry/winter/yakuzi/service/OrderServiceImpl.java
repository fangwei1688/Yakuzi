package com.cherry.winter.yakuzi.service;

import com.cherry.winter.yakuzi.Message.ApplicationException;
import com.cherry.winter.yakuzi.Message.ErrorMessages;
import com.cherry.winter.yakuzi.model.OrderInfo;
import com.cherry.winter.yakuzi.model.ProductInfo;
import com.cherry.winter.yakuzi.model.TicketInfo;
import com.cherry.winter.yakuzi.redis.KeyUtils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/14.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

  private final long PAY_DEAD_LINE = 900_000l;

  @Resource(name = "redisTemplate")
  RedisTemplate redisTemplate;

  @Resource(name = "productService")
  ProductService productService;
  @Resource(name = "ticketService")
  TicketService ticketService;

  @Override
  public OrderInfo createOrder(String ticketId) throws ApplicationException {
    // ticket 的有效性检查
    TicketInfo ticketInfo = ticketService.getTicketInfo(ticketId);
    if (ticketInfo == null) {
      throw new ApplicationException(ErrorMessages.TICKET_IS_NOT_EXIST);
    }
    if (ticketInfo.getExpireAt() < System.currentTimeMillis()) {
      throw new ApplicationException(ErrorMessages.TICKET_IS_EXPIRED);
    }

    ProductInfo productInfo = productService.getProductInfo(ticketInfo.getProductId());
    if(productInfo==null){
      throw new ApplicationException(ErrorMessages.PRODUCT_IS_NOT_EXIST);
    }

    //只有ticketId 排号在0~商品总数的范围内才可下单
    if (!ticketService.getTicketList(productInfo.getProductId(),0,productInfo.getAmount()).contains(ticketId)) {
      throw new ApplicationException(ErrorMessages.PRODUCT_SALE_OUT);
    }

    OrderInfo orderInfo = new OrderInfo();
    //should get from impulse sender,just for test here
    orderInfo.setOrderId(System.currentTimeMillis());
    orderInfo.setCreateAt(System.currentTimeMillis());
    //5 minute to prepare pay
    orderInfo.setExpiredAt(orderInfo.getCreateAt() + PAY_DEAD_LINE);
    orderInfo.setLastUpdateAt(orderInfo.getCreateAt());
    orderInfo.setUserId(ticketInfo.getUserId());
    orderInfo.setTradeFee(productInfo.getPrice());
    orderInfo.setProductId(ticketInfo.getProductId());
    if (redisTemplate.boundValueOps(KeyUtils.ticketInfo(ticketId))
        .setIfAbsent(orderInfo.getOrderId())) {
      saveOrderInfo(orderInfo);
      redisTemplate.boundValueOps(KeyUtils.ticketOrder(ticketId)).set(orderInfo.getOrderId());
    } else {
      throw new ApplicationException(ErrorMessages.INVALID_TICKET);
    }

    return orderInfo;
  }

  private void saveOrderInfo(OrderInfo orderInfo) {
    Map<String, Object> map = new JacksonHashMapper(OrderInfo.class).toHash(orderInfo);
    redisTemplate.boundHashOps(KeyUtils.orderInfo(orderInfo.getOrderId())).putAll(map);
    //link user --> order
    redisTemplate.boundZSetOps(KeyUtils.userOrderZSet(orderInfo.getUserId()))
        .add(orderInfo.getOrderId(), orderInfo.getLastUpdateAt());
  }

  @Override
  public OrderInfo getOrder(long orderId) {
    Map<String, Object> map = redisTemplate.boundHashOps(KeyUtils.orderInfo(orderId)).entries();
    if (map == null || map.isEmpty()) {
      throw new ApplicationException(ErrorMessages.PRODUCT_IS_NOT_EXIST);
    }
    return (OrderInfo) new JacksonHashMapper(OrderInfo.class).fromHash(map);
  }

  @Override
  public OrderInfo getOrder(String ticketId) {
    Object obj = redisTemplate.boundValueOps(KeyUtils.ticketOrder(ticketId)).get();
    if (obj == null) {
      return null;
    } else {
      return getOrder(Long.parseLong(obj.toString()));
    }
  }
}
