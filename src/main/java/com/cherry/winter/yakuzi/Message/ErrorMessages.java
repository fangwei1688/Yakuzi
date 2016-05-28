package com.cherry.winter.yakuzi.Message;

import javax.ws.rs.core.Response;

/**
 * Created by Fangwei on 15/7/12.
 */
public enum ErrorMessages {
  INTERNAL_SERVER_ERROR(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() * 10000,
      Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase()),

  //ticket
  TICKET_IS_NOT_EXIST(40400000, "ticket is not exist"),
  TICKET_IS_EXPIRED(40300001, "ticket is expired"),
  INVALID_TICKET(40300002, "invalid ticket"),

  //product
  PRODUCT_IS_NOT_EXIST(40401000, "product is not exist"),
  PRODUCT_IS_NOT_ON_SALE(40401001, "product is not on sale"),
  PRODUCT_SALE_OUT(40401002, "product sale out");

  //order

  //user

  private int errorCode;
  private String message;

  ErrorMessages(int errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
