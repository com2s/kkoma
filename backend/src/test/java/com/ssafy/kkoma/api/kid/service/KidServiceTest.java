package com.ssafy.kkoma.api.kid.service;

import com.ssafy.kkoma.api.kid.dto.request.UpdateKidRequest;
import com.ssafy.kkoma.api.kid.dto.response.KidSummaryResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KidServiceTest {

    @Autowired
    KidService kidService;

    @Autowired
    MemberService memberService; // todo mocking

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void 아이_아이디_없이_아이_정보_수정하기_시_자동으로_아이를_생성한다() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);

        String NEW_KID_NAME = "kim";
        LocalDate NEW_KID_BIRTH_DATE = LocalDate.now();
        GenderType NEW_KID_GENDER = GenderType.MALE;
        UpdateKidRequest updateKidRequest = UpdateKidRequest.builder()
                .name(NEW_KID_NAME)
                .birthDate(NEW_KID_BIRTH_DATE)
                .gender(NEW_KID_GENDER)
                .build();

        // when
        KidSummaryResponse kidSummaryResponse = kidService.updateKidInfo(savedMember.getId(), updateKidRequest);

        // then
        assertEquals(NEW_KID_NAME, kidSummaryResponse.getName());
        assertEquals(NEW_KID_BIRTH_DATE, kidSummaryResponse.getBirthDate());
        assertEquals(NEW_KID_GENDER, kidSummaryResponse.getGender());
    }

    @Test
    @Transactional
    void 아이_정보_수정하기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        Kid kid = memberService.createKid(savedMember.getId());

        String NEW_KID_NAME = "kim";
        LocalDate NEW_KID_BIRTH_DATE = LocalDate.now();
        GenderType NEW_KID_GENDER = GenderType.MALE;
        UpdateKidRequest updateKidRequest = UpdateKidRequest.builder()
                .name(NEW_KID_NAME)
                .birthDate(NEW_KID_BIRTH_DATE)
                .gender(NEW_KID_GENDER)
                .build();

        // when
        KidSummaryResponse kidSummaryResponse = kidService.updateKidInfo(kid.getId(), savedMember.getId(), updateKidRequest);

        // then
        assertEquals(NEW_KID_NAME, kidSummaryResponse.getName());
        assertEquals(NEW_KID_BIRTH_DATE, kidSummaryResponse.getBirthDate());
        assertEquals(NEW_KID_GENDER, kidSummaryResponse.getGender());
    }

    @Test
    @Transactional
    void 잘못된_아이_아이디로_수정하기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        Kid kid = memberService.createKid(savedMember.getId());

        String NEW_KID_NAME = "kim";
        LocalDate NEW_KID_BIRTH_DATE = LocalDate.now();
        GenderType NEW_KID_GENDER = GenderType.MALE;
        UpdateKidRequest updateKidRequest = UpdateKidRequest.builder()
                .name(NEW_KID_NAME)
                .birthDate(NEW_KID_BIRTH_DATE)
                .gender(NEW_KID_GENDER)
                .build();

        // when&then
        assertThrows(EntityNotFoundException.class, () -> kidService.updateKidInfo(kid.getId() + 100000000L, savedMember.getId(), updateKidRequest));
    }

    @Test
    @Transactional
    void getKids() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        List<Kid> kids = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Kid kid = memberService.createKid(savedMember.getId());
            UpdateKidRequest updateKidRequest = UpdateKidRequest.builder()
                    .name("KID_" + i)
                    .build();
            kidService.updateKidInfo(kid.getId(), member.getId(), updateKidRequest);
            kids.add(kid);
        }

        // when
        List<KidSummaryResponse> kidSummaryResponses = kidService.getKids(savedMember.getId());

        // then
        for (int i = 0; i < 6; i++) {
            assertEquals("KID_" + i, kidSummaryResponses.get(i).getName());
        }
    }

    @Test
    void 아이_요약_정보_얻기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        Kid kid = memberService.createKid(savedMember.getId());

        // when
        KidSummaryResponse kidSummaryResponse = kidService.getKid(kid.getId(), member.getId());

        // then
        assertEquals(kid.getName(), kidSummaryResponse.getName());
        assertEquals(kid.getBirthDate(), kidSummaryResponse.getBirthDate());
        assertEquals(kid.getGender(), kidSummaryResponse.getGender());
    }

    @Test
    void 내_아이가_아닌_아이의_아이디로_아이_요약_정보_얻기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        Kid kid = memberService.createKid(savedMember.getId());

        // when&then
        assertThrows(BusinessException.class, () -> kidService.getKid(kid.getId() + 100000000L, member.getId()));
    }

}
