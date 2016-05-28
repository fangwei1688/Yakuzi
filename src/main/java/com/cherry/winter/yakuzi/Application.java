package com.cherry.winter.yakuzi;

import com.cherry.winter.yakuzi.utils.ApplicationInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {
  @Autowired
  ApplicationInterceptor applicationInterceptor;

  public static void main(String[] args) throws Exception {
    SpringApplication app = new SpringApplication(Application.class);
    app.run(args);
  }

  /**
   *
   * @param registry
   */
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(applicationInterceptor);
  }

}