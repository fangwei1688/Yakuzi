package com.cherry.winter.yakuzi.controller;

import com.cherry.winter.yakuzi.model.AuthorityType;
import com.cherry.winter.yakuzi.service.TicketService;
import com.cherry.winter.yakuzi.utils.FireAuthority;
import com.cherry.winter.yakuzi.utils.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/8.
 */
@RestController
public class TicketController {

  @Resource(name = "ticketService")
  TicketService ticketService;

  @RequestMapping(value = "/ticket", method = RequestMethod.POST)
  @FireAuthority(authorityTypes = {AuthorityType.LOGIN})

  @ResponseBody
  String getTicket(
      @RequestParam(required = true, name = "productId")
      long productId,
      @RequestAttribute("userId")
      long userId) {
    return ticketService.createTicket(userId, productId);
  }
}
