package com.cherry.winter.yakuzi.service;

import com.cherry.winter.yakuzi.model.OrderInfo;

/**
 * Created by Fangwei on 16/5/14.
 */
public interface OrderService {
  OrderInfo createOrder(String ticketId);

  OrderInfo getOrder(long orderId);

  OrderInfo getOrder(String ticketId);


}
