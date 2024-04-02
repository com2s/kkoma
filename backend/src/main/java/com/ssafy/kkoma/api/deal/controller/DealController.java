package com.ssafy.kkoma.api.deal.controller;

import com.ssafy.kkoma.api.deal.service.DealService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Deal")
@RestController
@RequiredArgsConstructor
public class DealController {

	private final DealService dealService;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Tag(name = "Deal")
	@Operation(
		summary = "거래 코드 조회",
		description = "[[노션](https://www.notion.so/todays-jiwoo/2b5ad0af9abe4f1086c0fa20e63250f9?pvs=4)] 구매자는 거래 코드를 담고 있는 QR코드를 조회할 수 있다.",
		security = { @SecurityRequirement(name = "bearer-key") }
	)
	@GetMapping("/api/deals/{dealId}/code")
	public ResponseEntity<ApiUtils.ApiResult<String>> getDealCode(
			@PathVariable("dealId") Long dealId,
			@MemberInfo MemberInfoDto memberInfoDto
	) {
		return ResponseEntity.ok(ApiUtils.success(dealService.getCode(dealId, memberInfoDto.getMemberId())));
	}

	@Tag(name = "Deal")
	@Operation(
		summary = "거래 완료 처리",
		description = "[[노션](https://www.notion.so/todays-jiwoo/0a4d8787ee1a48a6983a52be2e8a1a9c?pvs=4)] 판매자는 구매자가 보여준 QR코드를 촬영해 거래를 마무리한다.",
		security = { @SecurityRequirement(name = "bearer-key") }
	)
	@PostMapping("/api/deals/{dealId}/accept")
	public ResponseEntity<?> finishDealSeller(
		@PathVariable("dealId") Long dealId,
		@MemberInfo MemberInfoDto memberInfoDto,
		@RequestParam("code") String code
	){
		dealService.finishDeal(dealId, memberInfoDto.getMemberId(), code);
		finishDealBuyer(dealId);
		return ResponseEntity.ok(ApiUtils.success(dealId));
	}

	@MessageMapping("/deals/{dealId}")
	public void finishDealBuyer(@DestinationVariable("dealId") Long dealId) {
		simpMessagingTemplate.convertAndSend("/topic/deals/" + dealId, ApiUtils.success(dealId));
	}
}
