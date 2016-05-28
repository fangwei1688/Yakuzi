package com.cherry.winter.yakuzi.controller;

import com.cherry.winter.yakuzi.Message.ApplicationException;
import com.cherry.winter.yakuzi.model.AuthorityType;
import com.cherry.winter.yakuzi.model.OrderInfo;
import com.cherry.winter.yakuzi.service.OrderService;
import com.cherry.winter.yakuzi.utils.FireAuthority;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/14.
 */
@RestController
public class OrderController {

  @Resource(name = "orderService")
  OrderService orderService;

  @RequestMapping(value = "/order", method = RequestMethod.POST)
  @FireAuthority(authorityTypes = {AuthorityType.SALES_ORDER_CREATE})
  @ResponseBody
  OrderInfo create(
      @RequestParam(required = true, name = "ticketId")
      String ticketId) throws ApplicationException {
    return orderService.createOrder(ticketId);
  }
}
