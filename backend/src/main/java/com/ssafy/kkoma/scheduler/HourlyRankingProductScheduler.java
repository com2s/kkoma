package com.ssafy.kkoma.scheduler;

import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyViewedResponse;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWishedResponse;
import com.ssafy.kkoma.api.product.service.ProductService;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul")
    public void getMostWishedProducts() {
        log.info("[scheduler] getMostWishedProducts가 실행됩니다. at {}", LocalDateTime.now().withSecond(1));
        List<ProductHourlyWishedResponse> productList = productService.getHourlyMostWishedProducts(4, LocalDateTime.now());
        log.info("[wish] result 개수 {}", productList.size());
        redisService.setValues("hourlyWishedProductList", productList);
    }

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul")
    public void getMostViewedProducts() {
        log.info("[scheduler] getMostViewedProducts가 실행됩니다. at {}", LocalDateTime.now().withSecond(1));
        List<ProductHourlyViewedResponse> productList = productService.getHourlyMostViewedProducts(4, LocalDateTime.now());
        log.info("[view] result 개수 {}", productList.size());
        redisService.setValues("hourlyViewedProductList", productList);
    }
}
