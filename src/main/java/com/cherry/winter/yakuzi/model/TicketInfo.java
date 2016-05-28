package com.cherry.winter.yakuzi.model;

/**
 * Created by Fangwei on 16/5/14.
 */
public class TicketInfo {
  private String id;
  private long userId;
  private long productId;
  private long expireAt;
  private long createAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public long getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(long expireAt) {
    this.expireAt = expireAt;
  }
}
