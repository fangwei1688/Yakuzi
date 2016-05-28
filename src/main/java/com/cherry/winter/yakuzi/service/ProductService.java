package com.cherry.winter.yakuzi.service;

import com.cherry.winter.yakuzi.model.ProductInfo;

import java.util.List;

/**
 * Created by Fangwei on 16/5/15.
 */
public interface ProductService {
  void createProduct(ProductInfo productInfo);

  ProductInfo getProductInfo(long productId);

  List<ProductInfo> getActivityProductInfoList(long start, long end);
}
