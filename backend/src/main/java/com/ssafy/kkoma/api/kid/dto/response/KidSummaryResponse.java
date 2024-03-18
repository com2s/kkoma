package com.ssafy.kkoma.api.kid.dto.response;

import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class KidSummaryResponse {

    private String name;
    private LocalDate birthDate;
    private GenderType gender;

    public static KidSummaryResponse fromEntity(Kid savedKid) {
            return KidSummaryResponse.builder()
                    .name(savedKid.getName())
                    .birthDate(savedKid.getBirthDate())
                    .gender(savedKid.getGender())
                    .build();
    }

}
