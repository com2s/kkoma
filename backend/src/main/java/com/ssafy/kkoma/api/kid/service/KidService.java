package com.ssafy.kkoma.api.kid.service;

import com.ssafy.kkoma.api.kid.dto.KidInfoResponseDto;
import com.ssafy.kkoma.api.kid.dto.UpdateKidRequestDto;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.kid.repository.KidRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.service.MemberService;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KidService {

    private final MemberService memberService;
    private final KidRepository kidRepository;

    public Kid createKid(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Kid kid = new Kid();
        Kid savedKid = kidRepository.save(kid);
        savedKid.setMember(member);
        return savedKid;
    }

    public KidInfoResponseDto updateKidInfo(Long kidId, UpdateKidRequestDto updateKidRequestDto) {
        Kid kid = findKidByKidId(kidId);
        kid.updateKidInfo(updateKidRequestDto);
        Kid savedKid = kidRepository.save(kid);
        return KidInfoResponseDto.of(savedKid);
    }

    private Kid findKidByKidId(Long kidId) {
        return kidRepository.findById(kidId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.KID_NOT_EXIST));
    }

}
