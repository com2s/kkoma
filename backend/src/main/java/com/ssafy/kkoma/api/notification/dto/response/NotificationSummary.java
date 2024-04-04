package com.ssafy.kkoma.api.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationSummary {
	private Long id;
	private String message;
	private String destination;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime sentAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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
