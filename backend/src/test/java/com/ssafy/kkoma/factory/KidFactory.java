package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.kid.repository.KidRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.global.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class KidFactory {

    @Autowired
    KidRepository kidRepository;

    public KidFactory(KidRepository kidRepository) {
        this.kidRepository = kidRepository;
    }

    public Kid createKid(Member member) {
        Kid kid = Kid.builder()
                .name(RandomStringGenerator.randomUUID(10))
                .gender(GenderType.UNKNOWN)
                .birthDate(LocalDate.now())
                .build();
        kid.setMember(member);
        return kidRepository.save(kid);
    }

    public Kid createKid(Member member, GenderType genderType, LocalDate birthDate) {
        Kid kid = Kid.builder()
                .name(RandomStringGenerator.randomUUID(10))
                .gender(genderType)
                .birthDate(birthDate)
                .build();
        kid.setMember(member);
        return kidRepository.save(kid);
    }

}
