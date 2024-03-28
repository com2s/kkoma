package com.ssafy.kkoma.api.notification.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.notification.repository.NotificationRepository;

@SpringBootTest
class NotificationServiceTest {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	NotificationService notificationService;

	@Autowired
	MemberService memberService;

	@Test
	@Transactional
	public void 알림_전체_조회() throws Exception {
		// given
		Member member = createMember("hi");
		Notification noti = createNoti(member);

		// when
		notificationRepository.save(noti);

		// then
		assertEquals(1, notificationService.getNotifications(member.getId()).size());
	}

	/*---------------------------------------------------------------------------------------------------------------*/

	private Notification createNoti(Member member) {
		return Notification.builder()
			.member(member)
			.message("임의로 만든 알림 메시지")
			.sentAt(LocalDateTime.now())
			.build();
	}

	private Member createMember(String email) {
		return memberService.registerMember(
			Member.builder().name("temp").email(email).memberType(MemberType.KAKAO).role(Role.USER).build()
		);
	}
}
