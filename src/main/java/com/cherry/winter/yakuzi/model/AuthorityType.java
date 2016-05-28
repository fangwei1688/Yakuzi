package com.cherry.winter.yakuzi.model;

/**
 * 权限枚举
 */
public enum AuthorityType {
  LOGIN("登陆授权", 0),
  WORKER("增删改查员工", 1),
  SALES_ORDER_CREATE("创建订单", 2),
  SALES_ORDER_FIND("查看订单", 3),
  SALES_ORDER_MODIFY("修改订单", 4),
  SALES_ORDER_DELETE("删除订单", 5),;
  private String name;
  private int index;

  private AuthorityType(String name, int index) {
    this.name = name;
    this.index = index;
  }

  private static AuthorityType valueByIndex(int index) {
    if (index > AuthorityType.values().length || index < 1) {
      return null;
    }
    return AuthorityType.values()[index - 1];
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}