package com.ssafy.kkoma.global.config;

import com.ssafy.kkoma.global.error.FeignClientExceptionErrorDecoder;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(basePackages = "com.ssafy.kkoma") // package name
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3);
    }

}
