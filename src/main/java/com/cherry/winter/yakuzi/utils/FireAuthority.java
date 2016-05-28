package com.cherry.winter.yakuzi.utils;

import com.cherry.winter.yakuzi.model.AuthorityType;
import com.cherry.winter.yakuzi.model.ResultType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Fangwei on 15/7/12.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FireAuthority {
  AuthorityType[] authorityTypes();

  ResultType resultType() default ResultType.JSON;
}
