package com.cherry.winter.yakuzi.redis;

/**
 * Created by Fangwei on 16/5/14.
 */
public class KeyUtils {
  static final String PRODUCT = "product:";
  static final String TICKET = "ticket:";
  static final String USER = "user:";
  static final String ORDER = "order:";
  static final String OAUTH = "oauth:";


  public static String productInfo(long productId) {
    return PRODUCT + productId + ":info";
  }

  public static String productTicketIdList(long productId) {
    return PRODUCT + productId + ":ticket:id:list";
  }

  public static String userTicketId(long userId, long productId) {
    return USER + userId + ":product:id:" + productId + ":ticket:id";
  }

  public static String ticketInfo(String ticketId) {
    return TICKET + ticketId + ":info";
  }

  public static String ticketOrder(String ticketId) {
    return TICKET + ticketId + ":order:id";
  }


  public static String orderInfo(long orderId) {
    return ORDER + orderId + ":info";
  }


  public static String userOrderZSet(long userId) {
    return USER + userId + ":order:id:zset";
  }

  public static String activityProductList() {
    return PRODUCT + ":activity:id:list";
  }

  public static String oauthInfo(String accessToken) {
    return "access:token:" + accessToken + ":" + OAUTH + "info";
  }

}
