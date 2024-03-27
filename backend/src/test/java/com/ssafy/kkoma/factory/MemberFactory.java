package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.point.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MemberFactory {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointRepository pointRepository;

    public MemberFactory(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember() {

        Member member = Member.builder()
                .name(randomUUID(10))
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .email(randomUUID(10) + "@kakao.com")
                .build();

        member.setKidInfoCompleted(true);
        Point point = new Point();
        Point savedPoint = pointRepository.save(point);
        member.setPoint(savedPoint);

        return memberRepository.save(member);
    }

    public static String randomUUID(int length) {
        // UUID 생성
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // 하이픈 제거
        String uuidWithoutHyphens = uuidString.replace("-", "");

        // 앞 부분을 length 만큼 추출
        String randomString = uuidWithoutHyphens.substring(0, length);

        return randomString;
    }

}
