package com.cherry.winter.yakuzi.service;

import com.cherry.winter.yakuzi.model.OauthInfo;
import com.cherry.winter.yakuzi.redis.KeyUtils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/22.
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {
  @Resource(name = "redisTemplate")
  RedisTemplate redisTemplate;

  @Override
  public String loginAndGetAccessToken(String account, String password) {
    OauthInfo oauthInfo = new OauthInfo();
    //just for test
    oauthInfo.setUserId(System.currentTimeMillis());
    oauthInfo.setAccount(account);
    oauthInfo.setPassword(password);
    oauthInfo.setRightContent("1111111");
    String accessToken = UUID.randomUUID().toString();
    saveOauthInfo( accessToken,  oauthInfo);
    return accessToken;
  }

  private void saveOauthInfo(String accessToken, OauthInfo oauthInfo) {
    Map<String, Object> map = new JacksonHashMapper(OauthInfo.class).toHash(oauthInfo);
    redisTemplate.boundHashOps(KeyUtils.oauthInfo(accessToken)).putAll(map);
  }

  @Override
  public OauthInfo getOauthInfo(String accessToken) {
    Map<String, Object> map = redisTemplate.boundHashOps(KeyUtils.oauthInfo(accessToken)).entries();
    if (map == null || map.isEmpty()) {
      return null;
    }
    return (OauthInfo) new JacksonHashMapper(OauthInfo.class).fromHash(map);
  }
}
