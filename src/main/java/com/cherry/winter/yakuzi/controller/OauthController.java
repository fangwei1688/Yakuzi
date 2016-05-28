package com.cherry.winter.yakuzi.controller;

import com.cherry.winter.yakuzi.Message.ApplicationException;
import com.cherry.winter.yakuzi.service.OauthService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/22.
 */
@RestController
public class OauthController {

  @Resource(name = "oauthService")
  OauthService oauthService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  String login(
      @RequestParam(required = true, name = "account")
      String account,
      @RequestParam(required = true, name = "password")
      String password) throws ApplicationException {
    return oauthService.loginAndGetAccessToken(account, password);
  }
}
