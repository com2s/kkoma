package com.ssafy.kkoma.api.offer.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class OfferTimeRequest {

    private LocalDate offerDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public OfferTimeRequest(LocalDate offerDate, LocalTime startTime, LocalTime endTime){
        this.offerDate = offerDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private List<OfferTimeRequest> offerTimeRequestList;
}
