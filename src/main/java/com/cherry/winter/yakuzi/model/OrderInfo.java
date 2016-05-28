package com.cherry.winter.yakuzi.model;

/**
 * Created by Fangwei on 16/5/14.
 */
public class OrderInfo {
  private long orderId;
  private long userId;
  private long productId;
  private int tradeFee;
  private long createAt;
  private long expiredAt;
  private long lastUpdateAt;

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public int getTradeFee() {
    return tradeFee;
  }

  public void setTradeFee(int tradeFee) {
    this.tradeFee = tradeFee;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }

  public long getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(long expiredAt) {
    this.expiredAt = expiredAt;
  }

  public long getLastUpdateAt() {
    return lastUpdateAt;
  }

  public void setLastUpdateAt(long lastUpdateAt) {
    this.lastUpdateAt = lastUpdateAt;
  }
}
