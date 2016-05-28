package com.cherry.winter.yakuzi.utils;

import com.cherry.winter.yakuzi.Message.ApplicationException;
import com.cherry.winter.yakuzi.Message.ErrorMessages;
import com.cherry.winter.yakuzi.model.AuthorityType;
import com.cherry.winter.yakuzi.model.OauthInfo;
import com.cherry.winter.yakuzi.model.ResultType;
import com.cherry.winter.yakuzi.service.OauthService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Fangwei on 15/7/12.
 */
@Component
public class ApplicationInterceptor extends HandlerInterceptorAdapter {

  final Logger logger = LoggerFactory.getLogger(getClass());

  @Resource(name = "oauthService")
  OauthService oauthService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    FireAuthority fireAuthority = getFireAuthority(handler);
    if (null == fireAuthority) {
      //public api
      return true;
    }
    logger.debug("fireAuthority:{}", fireAuthority.toString());

    boolean flag = false;
    String accessToken = request.getHeader("accessToken");
    if (StringUtils.isBlank(accessToken)) {
      response(fireAuthority, request, response, HttpStatus.FORBIDDEN.value(),
          HttpStatus.FORBIDDEN.name());
      return false;
    }
    OauthInfo oauthInfo = oauthService.getOauthInfo(accessToken);
    if (oauthInfo == null) {
      response(fireAuthority, request, response, HttpStatus.FORBIDDEN.value(),
          HttpStatus.FORBIDDEN.name());
      return false;
    }

    for (AuthorityType at : fireAuthority.authorityTypes()) {
      if (AuthorityHelper.hasAuthority(at, oauthInfo.getRightContent())) {
        flag = true;
        break;
      }
    }

    if (!flag) {
      response(fireAuthority, request, response, HttpStatus.FORBIDDEN.value(),
          HttpStatus.FORBIDDEN.name());
      return false;
    }
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    if (ex != null) {
      FireAuthority fireAuthority = getFireAuthority(handler);
      if (ex instanceof ApplicationException) {
        ApplicationException applicationException = (ApplicationException) ex;
        response(fireAuthority, request, response, applicationException.getErrorCode(),
            applicationException.getMessage());
      } else {
        response(fireAuthority, request, response,
            ErrorMessages.INTERNAL_SERVER_ERROR.getErrorCode(),
            ErrorMessages.INTERNAL_SERVER_ERROR.getMessage());
      }
    }
  }

  /**
   * application exception handler
   */
  private void response(FireAuthority fireAuthority, HttpServletRequest request,
      HttpServletResponse response, int code, String message) throws IOException {
    if (fireAuthority != null && fireAuthority.resultType() == ResultType.PAGE) {
      StringBuilder sb = new StringBuilder();
      sb.append(request.getContextPath());
      sb.append("/error.jsp");
      sb.append("?message=").append(message);
      sb.append("&code=").append(code);
      response.sendRedirect(sb.toString());
    } else {
      response.setCharacterEncoding("utf-8");
      response.setContentType("application/json;charset=UTF-8");
      OutputStream out = response.getOutputStream();
      PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
      pw.println("{\"code\":" + code + ",\"message\":\"" + message + "\"}");
      pw.flush();
      pw.close();
    }
  }

  /**
   * @param handler
   * @return
   */
  private FireAuthority getFireAuthority(Object handler) {
    HandlerMethod handler2 = (HandlerMethod) handler;
    return handler2.getMethodAnnotation(FireAuthority.class);
  }
}