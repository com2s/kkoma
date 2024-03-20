package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.domain.point.repository.PointRepository;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.kid.repository.KidRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.AuthenticationException;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final KidRepository kidRepository;

    public Member registerMember(Member member) {
        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);
        createKid(savedMember.getId());
        createPoint(savedMember.getId());
        return savedMember;
    }

    public Kid createKid(Long memberId) {
        Member member = findMemberByMemberId(memberId);
        Kid kid = new Kid();
        Kid savedKid = kidRepository.save(kid);
        savedKid.setMember(member);
        return savedKid;
    }

    public Point createPoint(Long memberId) {
        Member savedMember = findMemberByMemberId(memberId);
        Point point = new Point();
        Point savedPoint = pointRepository.save(point);
        savedMember.setPoint(savedPoint);
        return savedPoint;
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if(optionalMember.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member findMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
        if(tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }

    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));
    }

    public MemberInfoResponse updateMemberInfo(Long memberId, UpdateMemberRequest updateMemberRequest) {
        Member member = findMemberByMemberId(memberId);
        member.updateMemberInfo(updateMemberRequest);
        Member savedMember;
        try {
            savedMember = memberRepository.save(member);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UPDATE_FAIL);
        }
        return MemberInfoResponse.fromEntity(savedMember);
    }

    public int getPointBalance(Long memberId) {
        Member member = findMemberByMemberId(memberId);
        return member.getPoint().getBalance();
    }

}
