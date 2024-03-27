package com.ssafy.kkoma.api.kid.service;

import com.ssafy.kkoma.api.kid.dto.response.KidSummaryResponse;
import com.ssafy.kkoma.api.kid.dto.request.UpdateKidRequest;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.kid.repository.KidRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KidService {

    private final KidRepository kidRepository;
    private final MemberService memberService;

    public KidSummaryResponse updateKidInfo(Long memberId, UpdateKidRequest updateKidRequest) {
        Member member = memberService.findMemberByMemberId(memberId);
        List<Kid> kids = member.getKids();
        Kid kid;
        if (kids.isEmpty()) {
            kid = memberService.createKid(memberId);
        } else {
            kid = member.getKids().get(0);
        }
        kid.updateKidInfo(updateKidRequest);
        member.setKidInfoCompleted(true);
        return KidSummaryResponse.fromEntity(kid);
    }

    public KidSummaryResponse updateKidInfo(Long kidId, Long memberId, UpdateKidRequest updateKidRequest) {
        Member member = memberService.findMemberByMemberId(memberId);
        List<Kid> kids = member.getKids();
        for (Kid kid : kids) {
            if (kid.getId() == kidId) {
                kid.updateKidInfo(updateKidRequest);
                return KidSummaryResponse.fromEntity(kid);
            }
        }

        throw new EntityNotFoundException(ErrorCode.KID_NOT_MATCHED);
    }

    private Kid findKidByKidId(Long kidId) {
        return kidRepository.findById(kidId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.KID_NOT_EXIST));
    }

    public List<KidSummaryResponse> getKids(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);

        List<KidSummaryResponse> kidSummaryResponseDtos = new ArrayList<>();

        if (!member.isKidInfoCompleted()) {
            return kidSummaryResponseDtos;
        }

        List<Kid> kids = member.getKids();
        for (Kid kid : kids) {
            kidSummaryResponseDtos.add(KidSummaryResponse.fromEntity(kid));
        }

        return kidSummaryResponseDtos;
    }

    public KidSummaryResponse getKid(Long kidId, Long memberId) {
        Kid kid = findKidByKidId(kidId);
        if (kid.getMember().getId() != memberId) {
            throw new BusinessException(ErrorCode.KID_NOT_MATCHED);
        }
        return KidSummaryResponse.fromEntity(kid);
    }
}
