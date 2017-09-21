package com.cherry.winter.yakuzi.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author Fangwei
 * @date 16/5/15
 */
public class ProductInfo {
  @Min(1)
  @Max(99999999999999L)
  private long productId;
  @Min(1463928236_000L)
  @Max(9999999999_999L)
  private long startAt;
  @Min(1463928236_000L)
  @Max(9999999999_999L)
  private long endAt;
  @Min(1)
  @Max(999999)
  private int amount;
  @Min(1)
  @Max(999999999)
  private int price;

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public long getStartAt() {
    return startAt;
  }

  public void setStartAt(long startAt) {
    this.startAt = startAt;
  }

  public long getEndAt() {
    return endAt;
  }

  public void setEndAt(long endAt) {
    this.endAt = endAt;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
