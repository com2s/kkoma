package com.ssafy.kkoma.api.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class NotiDetail {
    private final String message;
    private final String destination;
}