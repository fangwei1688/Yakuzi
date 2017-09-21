package com.cherry.winter.yakuzi.controller;

import javax.annotation.Resource;

import com.cherry.winter.yakuzi.message.ApplicationException;
import com.cherry.winter.yakuzi.service.OauthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Fangwei on 16/5/22.
 */
@Controller
@RequestMapping(path = "/oauth")
public class OauthController {

    @Resource(name = "oauthService")
    OauthService oauthService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    String loginUser(
        @RequestParam(required = true, name = "account")
            String account,
        @RequestParam(required = true, name = "password")
            String password) throws ApplicationException {
        return oauthService.loginAndGetAccessToken(account, password);
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    @ResponseBody
    ModelAndView loginAdmin(
        @RequestParam(name = "account")
            String account,
        @RequestParam(name = "password")
            String password) throws ApplicationException {
        ModelAndView modelAndView = new ModelAndView();
        if (account.equals("test") && password.equals("123456")) {
            modelAndView.setViewName("redirect:/index.html");
        } else {
            modelAndView.setViewName("redirect:/login.html");
            modelAndView.addObject("alert-error","帐号或密码错误");
        }
        return modelAndView;
    }
}
