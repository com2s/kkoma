package com.ssafy.kkoma.api.point.dto.request;

import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class TransferPointRequest {

    private int amount;

    @Enumerated(EnumType.STRING)
    private PointChangeType type;
}
