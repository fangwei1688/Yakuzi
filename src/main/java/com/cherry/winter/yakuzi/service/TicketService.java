package com.cherry.winter.yakuzi.service;

import com.cherry.winter.yakuzi.model.TicketInfo;

import java.util.List;

/**
 * Created by Fangwei on 16/5/14.
 */
public interface TicketService {
  String createTicket(long userId, long productId);

  TicketInfo getTicketInfo(String ticketId);

  List<String> getTicketList(long productId, long start, long end);
}
