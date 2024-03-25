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
	@Operation(summary = "to get a code for the deal", security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping("{dealId}/code")
	public ResponseEntity<ApiUtils.ApiResult<String>> getDealCode(@PathVariable Long dealId, @MemberInfo MemberInfoDto memberInfoDto){
		return ResponseEntity.ok(ApiUtils.success(dealService.getCode(dealId, memberInfoDto.getMemberId())));
	}

	@Tag(name = "Deal")
	@Operation(summary = "to process deal completion", security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping("{dealId}/accept")
	public ResponseEntity<?> processDealCompletion(
		@PathVariable Long dealId,
		@MemberInfo MemberInfoDto memberInfoDto,
		@RequestParam String code
	){

		dealService.finishDeal(dealId, memberInfoDto.getMemberId(), code);
		return ResponseEntity.ok().build();
	}
}
