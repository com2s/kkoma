package com.ssafy.kkoma.api.redis.service;

import com.ssafy.kkoma.api.redis.constant.RedisKeyName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

    public void setValues(String key, Object data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, castToString(data));
    }

    public void setValues(String key, Object data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, castToString(data), duration);
    }

    @Transactional(readOnly = true)
    public String getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }
        return (String) values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public void expireValues(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }

    /*-------------------------------------------------------------------*/

    private String castToString(Object data) {
        if (data instanceof String) {
            return data.toString();
        }

        try {
            return mapper.writeValueAsString(data);
        } catch (IOException e) {
            log.warn("IOException 발생");
        }

        return "오류 발생";
    }

    public void saveHourlyData(RedisKeyName redisKeyName, List<?> data) {
        LocalDateTime now = LocalDateTime.now();
        int prevHour = now.minusHours(1).getHour();
        int prevPrevHour = now.minusHours(2).getHour();
        log.info("prevHour {}, prevPrevHour {}", prevHour, prevPrevHour);

        if (data.isEmpty()) { // 전 시간 데이터 복사해서 추가
            String prevPrevData = getValues(redisKeyName.toString() + prevPrevHour);
            setValues(redisKeyName.toString() + prevHour, prevPrevData);
            log.info("전 시간 데이터 복사해서 추가");
        } else {
            setValues(redisKeyName.toString() + prevHour, data);
            log.info("집계 데이터 추가");
        }

        deleteValues(redisKeyName.toString() + prevPrevHour);
    }

    public String findHourlyData(RedisKeyName redisKeyName, LocalDateTime now) {
        int prevHour = now.minusHours(1).getHour();
        return getValues(redisKeyName.toString() + prevHour);
    }

}
