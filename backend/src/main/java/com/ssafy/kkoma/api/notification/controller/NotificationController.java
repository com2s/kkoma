package com.ssafy.kkoma.api.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.notification.dto.response.NotificationSummary;
import com.ssafy.kkoma.api.notification.service.NotificationService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Notification")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	@Tag(name = "Notification")
	@Operation(
		summary = "알림 정보 전체 조회",
		description = "[[노션](https://www.notion.so/todays-jiwoo/8db42dc75220442e9772aaffbae509b7?pvs=4)] 유저가 수신한 알림 요약 전체 정보를 조회한다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@GetMapping("")
	public ResponseEntity<ApiUtils.ApiResult<List<NotificationSummary>>> getNotifications(
		@MemberInfo MemberInfoDto memberInfoDto
	) {
		List<NotificationSummary> notiList = notificationService.getNotifications(memberInfoDto.getMemberId());
		return ResponseEntity.ok(ApiUtils.success(notiList));
	}
}
