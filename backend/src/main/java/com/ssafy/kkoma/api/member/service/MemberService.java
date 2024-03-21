package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.point.repository.PointRepository;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.kid.repository.KidRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.AuthenticationException;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public List<ProductInfoResponse> getMySellingProducts(Long memberId, ProductType... productTypes) {
        Member member = findMemberByMemberId(memberId);
        List<Product> products = member.getProducts();
        List<ProductInfoResponse> productInfos = new ArrayList<>();

        // todo 고도화 (동적 쿼리 사용해서 DB에서 가져오는 단계에서 타입에 조건을 걸어서 조회)
        for (Product product : products) {
            for (ProductType productType : productTypes) {
                if (product.getStatus() == productType) {
                    productInfos.add(ProductInfoResponse.fromEntity(product, MyProductType.SELL));
                    break;
                }
            }

            if (productTypes.length == 0) {
                productInfos.add(ProductInfoResponse.fromEntity(product, MyProductType.SELL));
            }
        }

        return productInfos;
    }

    public List<Offer> getMyOffers(Long memberId) {
        Member member = findMemberByMemberId(memberId);

        return member.getOffers();
    }

}
