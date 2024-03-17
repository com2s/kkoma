package com.ssafy.kkoma.api.kid.service;

import com.ssafy.kkoma.api.kid.dto.KidSummaryResponseDto;
import com.ssafy.kkoma.api.kid.dto.UpdateKidRequestDto;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.kid.repository.KidRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.member.service.MemberService;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KidService {

    private final KidRepository kidRepository;
    private final MemberService memberService;

    public KidSummaryResponseDto updateKidInfo(Long memberId, UpdateKidRequestDto updateKidRequestDto) {
        Member member = memberService.findMemberByMemberId(memberId);
        List<Kid> kids = member.getKids();
        Kid kid;
        if (kids.isEmpty()) {
            kid = memberService.createKid(memberId);
        } else {
            kid = member.getKids().get(0);
        }
        kid.updateKidInfo(updateKidRequestDto);
        return KidSummaryResponseDto.of(kid);
    }

    public KidSummaryResponseDto updateKidInfo(Long kidId, Long memberId, UpdateKidRequestDto updateKidRequestDto) {
        Member member = memberService.findMemberByMemberId(memberId);
        List<Kid> kids = member.getKids();
        for (Kid kid : kids) {
            if (kid.getId() == kidId) {
                kid.updateKidInfo(updateKidRequestDto);
                return KidSummaryResponseDto.of(kid);
            }
        }

        throw new EntityNotFoundException(ErrorCode.KID_NOT_MATCHED);
    }

    private Kid findKidByKidId(Long kidId) {
        return kidRepository.findById(kidId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.KID_NOT_EXIST));
    }

    public List<KidSummaryResponseDto> getKids(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);

        List<KidSummaryResponseDto> kidSummaryResponseDtos = new ArrayList<>();
        List<Kid> kids = member.getKids();
        for (Kid kid : kids) {
            kidSummaryResponseDtos.add(KidSummaryResponseDto.of(kid));
        }

        return kidSummaryResponseDtos;
    }

    public KidSummaryResponseDto getKid(Long kidId, Long memberId) {
        Kid kid = findKidByKidId(kidId);
        if (kid.getMember().getId() != memberId) {
            throw new BusinessException(ErrorCode.KID_NOT_MATCHED);
        }
        return KidSummaryResponseDto.of(kid);
    }
}
