package com.ssafy.kkoma.api.kid.controller;

import com.ssafy.kkoma.api.kid.dto.KidInfoResponseDto;
import com.ssafy.kkoma.api.kid.dto.UpdateKidRequestDto;
import com.ssafy.kkoma.api.kid.service.KidService;
import com.ssafy.kkoma.api.member.dto.MemberInfoResponseDto;
import com.ssafy.kkoma.api.member.dto.UpdateMemberRequestDto;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kids")
@RequiredArgsConstructor
public class KidController {

    private final KidService kidService;

    @PutMapping
    public ResponseEntity<KidInfoResponseDto> updateKid(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequestDto updateKidRequestDto) {

        Long memberId = memberInfoDto.getMemberId();
        Kid kid = kidService.createKid(memberId);
        KidInfoResponseDto kidInfoResponseDto = kidService.updateKidInfo(kid.getId(), updateKidRequestDto);

        return ResponseEntity.ok(kidInfoResponseDto);
    }

}
