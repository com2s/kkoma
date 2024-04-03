package com.ssafy.kkoma.scheduler;

import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWished;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWishedResponse;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.api.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class HourlyRankingProductScheduler {

    // 1시간 마다
    // 1) 찜 수가 가장 많은 상품글 n개
    // 2) 조회수 가장 높은 상품글 n개
    // 3) 거래 요청이 가장 많은 상품글 n개
    // 를 조사한다.

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul") // 1시간 마다 실행 (01:00, 02:00, 03:00, ...)
    public void getMostWishedProducts() {
        log.info("[scheduler] getMostWishedProducts가 실행됩니다. at {}", LocalDateTime.now());
        List<ProductHourlyWishedResponse> productList = productService.getHourlyMostWishedProducts(20, LocalDateTime.now());
        redisService.setValues("hourlyWishedProductList", productList);
    }
}
