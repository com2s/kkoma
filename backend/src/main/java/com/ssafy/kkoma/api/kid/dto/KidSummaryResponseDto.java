package com.ssafy.kkoma.api.kid.dto;

import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class KidSummaryResponseDto {

    private String name;
    private LocalDate birthDate;
    private GenderType gender;

    public static KidSummaryResponseDto of(Kid savedKid) {
            return KidSummaryResponseDto.builder()
                    .name(savedKid.getName())
                    .birthDate(savedKid.getBirthDate())
                    .gender(savedKid.getGender())
                    .build();
    }

}
