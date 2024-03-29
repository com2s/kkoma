package com.ssafy.kkoma.api.notification.dto.response;

import java.time.LocalDateTime;

import com.ssafy.kkoma.domain.notification.entity.Notification;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationSummary {
	private Long id;
	private String message;
	private String destination;
	private LocalDateTime sentAt;
	private LocalDateTime readAt;

	public static NotificationSummary fromEntity(Notification noti) {
		return NotificationSummary.builder()
			.id(noti.getId())
			.message(noti.getMessage())
			.destination(noti.getDestination())
			.sentAt(noti.getSentAt())
			.readAt(noti.getReadAt())
			.build();
	}
}
