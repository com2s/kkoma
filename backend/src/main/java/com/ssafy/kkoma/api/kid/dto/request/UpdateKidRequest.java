package com.ssafy.kkoma.api.kid.dto.request;

import com.ssafy.kkoma.domain.kid.constant.GenderType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateKidRequest {

    private String name;
    private LocalDate birthDate;
    private GenderType gender;

}
