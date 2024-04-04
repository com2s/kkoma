package com.ssafy.kkoma.scheduler;

import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyViewedResponse;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWishedResponse;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.api.redis.constant.RedisKeyName;
import com.ssafy.kkoma.api.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class HourlyRankingProductScheduler {

    // 1시간 마다 (01:00, 02:00, 03:00, ...)
    // 1) 찜 수가 가장 많은 상품글 4개
    // 2) 조회수 가장 높은 상품글 4개
    // 3) 거래 요청이 가장 많은 상품글 4개
    // 를 조사한다.

    // hourlyWishedProductList13 = 13:00 ~ 13:59 기반 데이터

    // 0개라면 갱신하지 않는다..

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul")
    public void getMostWishedProducts() {
        log.info("[스케쥴러] 지난 1시간 찜수 기반 상품글 조회를 시작합니다 at {}", LocalDateTime.now().withSecond(1));
        List<ProductHourlyWishedResponse> productList = productService.getHourlyMostWishedProducts(4, LocalDateTime.now());

        redisService.saveHourlyData(RedisKeyName.hourlyWishedProductList, productList);
    }

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul")
    public void getMostViewedProducts() {
        log.info("[스케쥴러] 지난 1시간 조회수 기반 상품글 조회를 시작합니다 at {}", LocalDateTime.now().withSecond(1));
        List<ProductHourlyViewedResponse> productList = productService.getHourlyMostViewedProducts(4, LocalDateTime.now());

        redisService.saveHourlyData(RedisKeyName.hourlyViewedProductList, productList);
    }
}
