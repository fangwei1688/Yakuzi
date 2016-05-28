package com.cherry.winter.yakuzi.model;

/**
 * Created by Fangwei on 15/7/4.
 */
public class OauthInfo {
  private long userId;
  private String rightContent;
  private String account;
  private String password;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRightContent() {
    return rightContent;
  }

  public void setRightContent(String rightContent) {
    this.rightContent = rightContent;
  }
}
