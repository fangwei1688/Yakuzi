package com.cherry.winter.yakuzi.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import com.cherry.winter.yakuzi.message.ApplicationException;
import com.cherry.winter.yakuzi.message.ErrorMessages;
import com.cherry.winter.yakuzi.model.ProductInfo;
import com.cherry.winter.yakuzi.model.TicketInfo;
import com.cherry.winter.yakuzi.redis.KeyUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/15.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
  static Cache<Long, ProductInfo> PRODUCT_LOCAL_CACHE = CacheBuilder.newBuilder()
      .expireAfterWrite(1, TimeUnit.MINUTES).build();

  @Autowired
  @Resource(name = "redisTemplate")
  RedisTemplate redisTemplate;

  @Override
  public void createProduct(ProductInfo productInfo) {
    Map<String, Object> map = new JacksonHashMapper(TicketInfo.class).toHash(productInfo);
    redisTemplate.boundHashOps(KeyUtils.productInfo(productInfo.getProductId())).putAll(map);
    redisTemplate.boundListOps(KeyUtils.activityProductList())
        .rightPush(productInfo.getProductId());
    PRODUCT_LOCAL_CACHE.put(productInfo.getProductId(), productInfo);
  }

  @Override
  public ProductInfo getProductInfo(long productId) {
    ProductInfo productInfo = PRODUCT_LOCAL_CACHE.getIfPresent(productId);
    if (productInfo != null) {
      return productInfo;
    }
    Map<String, Object> map = redisTemplate.boundHashOps(KeyUtils.productInfo(productId)).entries();
    if (map == null || map.isEmpty()) {
      throw new ApplicationException(ErrorMessages.PRODUCT_IS_NOT_EXIST);
    }
    productInfo = (ProductInfo) new JacksonHashMapper(ProductInfo.class).fromHash(map);
    PRODUCT_LOCAL_CACHE.put(productId, productInfo);
    return productInfo;
  }

  @Override
  public List<ProductInfo> getActivityProductInfoList(long start, long end) {
    List<Long> productIdList = redisTemplate.boundListOps(KeyUtils.activityProductList())
        .range(start, end);
    List<ProductInfo> productInfoList = Lists.newArrayList();
    if (CollectionUtils.isNotEmpty(productIdList)) {
      for (Long productId : productIdList) {
        ProductInfo productInfo = getProductInfo(productId);
        if (productInfo == null || productInfo.getEndAt() < System.currentTimeMillis()) {
          redisTemplate.boundListOps(KeyUtils.activityProductList()).remove(1, productId);
        } else {
          productInfoList.add(productInfo);
        }
      }
    }
    return productInfoList;
  }
}
