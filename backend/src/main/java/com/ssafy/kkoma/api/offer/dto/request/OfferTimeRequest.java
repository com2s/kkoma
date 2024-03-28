package com.ssafy.kkoma.api.offer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfferTimeRequest {

    private LocalDate offerDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public OfferTimeRequest(LocalDate offerDate, LocalTime startTime, LocalTime endTime) {
        this.offerDate = offerDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private List<OfferTimeRequest> offerTimeRequestList;
}
