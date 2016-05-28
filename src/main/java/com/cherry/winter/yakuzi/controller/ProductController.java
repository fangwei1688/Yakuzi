package com.cherry.winter.yakuzi.controller;

import com.cherry.winter.yakuzi.Message.ApplicationException;
import com.cherry.winter.yakuzi.model.AuthorityType;
import com.cherry.winter.yakuzi.model.ProductInfo;
import com.cherry.winter.yakuzi.service.ProductService;
import com.cherry.winter.yakuzi.utils.FireAuthority;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by Fangwei on 16/5/14.
 */
@RestController
public class ProductController {

  @Resource(name = "productService")
  ProductService productService;

  @RequestMapping(value = "/product", method = RequestMethod.POST)
  @FireAuthority(authorityTypes = {AuthorityType.SALES_ORDER_CREATE})
  @ResponseBody
  ProductInfo create(
      @Valid
      ProductInfo productInfo) throws ApplicationException {
    productService.createProduct(productInfo);
    return productInfo;
  }
}
