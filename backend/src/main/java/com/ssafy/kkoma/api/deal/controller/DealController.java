package com.ssafy.kkoma.api.deal.controller;

import java.util.List;

import com.ssafy.kkoma.global.util.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.deal.service.DealService;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Deal")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deals")
public class DealController {

	private final ProductService productService;
	private final DealService dealService;

	@Tag(name = "Deal")
	@Operation(summary = "거래 코드 조회",
		description = "[[노션] 구매자는 거래 코드를 담고 있는 QR코드를 조회할 수 있다.](https://www.notion.so/todays-jiwoo/2b5ad0af9abe4f1086c0fa20e63250f9?pvs=4)",
		security = { @SecurityRequirement(name = "bearer-key") }
	)
	@GetMapping("{dealId}/code")
	public ResponseEntity<ApiUtils.ApiResult<String>> getDealCode(@PathVariable Long dealId, @MemberInfo MemberInfoDto memberInfoDto){
		return ResponseEntity.ok(ApiUtils.success(dealService.getCode(dealId, memberInfoDto.getMemberId())));
	}

	@Tag(name = "Deal")
	@Operation(summary = "거래 완료 처리",
		description = "[[노션] 판매자는 구매자가 보여준 QR코드를 촬영해 거래를 마무리한다.](https://www.notion.so/todays-jiwoo/0a4d8787ee1a48a6983a52be2e8a1a9c?pvs=4)",
		security = { @SecurityRequirement(name = "bearer-key") }
	)
	@PostMapping("{dealId}/accept")
	public ResponseEntity<?> processDealCompletion(
		@PathVariable Long dealId,
		@MemberInfo MemberInfoDto memberInfoDto,
		@RequestParam String code
	){
		dealService.finishDeal(dealId, memberInfoDto.getMemberId(), code);
		return ResponseEntity.ok(ApiUtils.success(dealId));
	}
}
