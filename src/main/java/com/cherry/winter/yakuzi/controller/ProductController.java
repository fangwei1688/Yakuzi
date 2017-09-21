package com.cherry.winter.yakuzi.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.cherry.winter.yakuzi.message.ApplicationException;
import com.cherry.winter.yakuzi.model.AuthorityType;
import com.cherry.winter.yakuzi.model.ProductInfo;
import com.cherry.winter.yakuzi.service.ProductService;
import com.cherry.winter.yakuzi.utils.FireAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Fangwei on 16/5/14.
 */
@Controller
@RequestMapping(path = "/product")
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
