package com.ssafy.kkoma.api.point.dto;

import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.PointHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointHistorySummary {
    private Long id;
    private int amount;
    private int balanceAfterChange;
    private PointChangeType pointChangeType;
    private LocalDateTime date;

    public static PointHistorySummary fromEntity(PointHistory pointHistory) {
        return PointHistorySummary.builder()
            .id(pointHistory.getId())
            .amount(pointHistory.getAmount())
            .balanceAfterChange(pointHistory.getBalanceAfterChange())
            .pointChangeType(pointHistory.getPointChangeType())
            .date(pointHistory.getCreatedAt())
            .build();
    }
}
