package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.area.service.AreaService;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberPreferredPlaceRequest;
import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberPreferredPlaceResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.dto.response.MyPageMemberProfileResponse;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.factory.AreaFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberInfoServiceTest {

    @Autowired
    MemberInfoService memberInfoService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    ProductFactory productFactory;

    @Autowired
    AreaService areaService;

    @Autowired
    AreaFactory areaFactory;

    @Test
    @Transactional
    void 회원_정보_얻기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        // when
        MemberInfoResponse memberInfo = memberInfoService.getMemberInfo(savedMember.getId());

        // then
        assertEquals(savedMember.getId(), memberInfo.getId());
        assertEquals(savedMember.getRole(), memberInfo.getRole());
    }

    @Test
    @Transactional
    void 존재하지않는_회원_정보_얻기() {
        // given
        Long NON_EXISTENT_MEMBER_ID = 100000000L;

        // when&then
        assertThrows(EntityNotFoundException.class, () -> memberInfoService.getMemberInfo(NON_EXISTENT_MEMBER_ID));
    }

    @Test
    @Transactional
    void 회원_요약_정보_얻기() {
        // given
        Area area = areaFactory.createArea();

        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        member.setPreferredPlaceRegionCode(area.getId());
        Member savedMember = memberRepository.save(member);

        // when
        MemberSummaryResponse memberSummary = memberInfoService.getMemberSummary(savedMember.getId());

        // then
        assertEquals(savedMember.getNickname(), memberSummary.getNickname());
        assertEquals(savedMember.getProfileImage(), memberSummary.getProfileImage());
    }

    @Test
    @Transactional
    void 존재하지않는_회원_요약_정보_얻기() {
        // given
        Long NON_EXISTENT_MEMBER_ID = 100000000L;

        // when&then
        assertThrows(EntityNotFoundException.class, () -> memberInfoService.getMemberSummary(NON_EXISTENT_MEMBER_ID));
    }

    @Test
    @Transactional
    public void 마이페이지_회원_프로필_정보_조회하기() throws Exception{
        // given
        Member member = memberFactory.createMember();
        MyPageMemberProfileResponse beforeProfile = memberInfoService.getMyPageMemberProfile(member.getId());

        // when
        final int COUNT = 10;

        for (int i = 0; i < COUNT; i++) {
            productFactory.createProduct(member);
        }

        MyPageMemberProfileResponse afterProfile = memberInfoService.getMyPageMemberProfile(member.getId());

        // then
        assertEquals(beforeProfile.getMyProductList().size() + COUNT, afterProfile.getMyProductList().size());
    }

    @Test
    @Transactional
    public void 회원_선호_거래_지역_정보_수정하기() throws Exception{
        // given
        Member member = memberFactory.createMember();
        final Long REGIONCODE = 10L;

        UpdateMemberPreferredPlaceRequest updateMemberPreferredPlaceRequest = UpdateMemberPreferredPlaceRequest.builder().preferredPlaceRegionCode(REGIONCODE).build();

        // when
        memberInfoService.updateMemberPreferredPlace(member.getId(), updateMemberPreferredPlaceRequest);

        // then
        assertEquals(REGIONCODE, memberRepository.findById(member.getId()).get().getPreferredPlaceRegionCode());
    }

    @Test
    @Transactional
    public void 회원_선호_거래_지역_정보_조회하기() throws Exception{
        // given
        Member member = memberFactory.createMember();
        final Long REGIONCODE = 10L;
        member.setPreferredPlaceRegionCode(REGIONCODE);

        // when
        MemberPreferredPlaceResponse memberPreferredPlace = memberInfoService.getMemberPreferredPlace(member.getId());

        // then
        assertEquals(REGIONCODE, memberPreferredPlace.getPreferredPlaceRegionCode());
    }
}
