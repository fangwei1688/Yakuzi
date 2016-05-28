package com.cherry.winter.yakuzi.utils;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Fangwei on 16/5/23.
 */
public class HandlerMethodArgumentResolverImpl implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    Annotation[] annotations = parameter.getParameterAnnotations();
    for (Annotation annotation : annotations) {
      if (RequestAttribute.class.isInstance(annotation)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Annotation[] annotations = parameter.getParameterAnnotations();
    for (Annotation annotation : annotations) {
      if (RequestAttribute.class.isInstance(annotation)) {
        RequestAttribute ra = (RequestAttribute) annotation;
        String name = ra.value();
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        return httpServletRequest.getAttribute(name);
      }
    }
    return null;
  }
}
