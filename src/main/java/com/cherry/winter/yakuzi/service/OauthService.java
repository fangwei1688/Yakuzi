package com.cherry.winter.yakuzi.service;

import com.cherry.winter.yakuzi.model.OauthInfo;

/**
 * Created by Fangwei on 16/5/22.
 */
public interface OauthService {
  String loginAndGetAccessToken(String account, String password);

  OauthInfo getOauthInfo(String accessToken);

}
