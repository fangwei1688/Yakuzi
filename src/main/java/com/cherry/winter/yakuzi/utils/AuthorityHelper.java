package com.cherry.winter.yakuzi.utils;

import com.cherry.winter.yakuzi.model.AuthorityType;

/**
 * Created by Fangwei on 15/7/12.
 */
public class AuthorityHelper {
  /**
   * 判断是否有权限
   *
   * @param authorityType 权限位
   * @param aString       权限字段,比如 11010101011101
   */
  public static boolean hasAuthority(AuthorityType authorityType, String aString) {
    if (aString == null || "".equals(aString)) {
      return false;
    }
    if (aString.length() < authorityType.getIndex()) {
      return false;
    }
    char value = aString.charAt(authorityType.getIndex());
    if (value == '1') {
      return true;
    }
    return false;
  }
}
