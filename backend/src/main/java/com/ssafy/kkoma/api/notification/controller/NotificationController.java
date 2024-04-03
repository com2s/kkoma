package com.ssafy.kkoma.api.notification.controller;

import java.util.List;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<ApiUtils.ApiResult<BasePageResponse<Notification, NotificationSummary>>> getNotifications(
		@MemberInfo MemberInfoDto memberInfoDto,
		Pageable pageable
	) {
		BasePageResponse<Notification, NotificationSummary> notiSummaryListResponse
			= notificationService.getNotifications(memberInfoDto.getMemberId(), pageable);
		return ResponseEntity.ok(ApiUtils.success(notiSummaryListResponse));
	}

	@Tag(name = "Notification")
	@Operation(
		summary = "미확인 알림 존재 여부 확인",
		description = "[[노션](https://www.notion.so/todays-jiwoo/a17aabe239434e26bba816aebd43f8cb?pvs=4)] ",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@GetMapping("/hasUnread")
	public ResponseEntity<ApiUtils.ApiResult<?>> hasUnreadNotis(
		@MemberInfo MemberInfoDto memberInfoDto
	) {
		Boolean hasUnread = notificationService.hasUnreadNotis(memberInfoDto.getMemberId());
		return ResponseEntity.ok(ApiUtils.success(hasUnread));
	}

	@Tag(name = "Notification")
	@Operation(
		summary = "알림 읽음 처리",
		description = "[[노션](https://www.notion.so/todays-jiwoo/bc29cfa76089469193f61073f956e55c?pvs=4)] 유저가 알림을 클릭하면 읽음으로 처리한다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@PostMapping("/read/{notificationId}")
	public ResponseEntity<ApiUtils.ApiResult<List<NotificationSummary>>> readNotification(
		@MemberInfo MemberInfoDto memberInfoDto,
		@PathVariable("notificationId") Long notificationId
	) {
		notificationService.readNotification(memberInfoDto.getMemberId(), notificationId);
		return ResponseEntity.ok(ApiUtils.success(null));
	}
}
