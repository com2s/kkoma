package com.ssafy.kkoma.api.notification.service;

import com.ssafy.kkoma.api.notification.dto.response.NotiDetail;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.notification.repository.NotificationRepository;
import com.ssafy.kkoma.factory.MemberFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@Slf4j
@SpringBootTest
class NotificationServiceTest {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	NotificationService notificationService;

	@Autowired
	MemberFactory memberFactory;

	@Test
	@Transactional
	public void 알림_생성과_전체_조회() throws Exception {
		int NUM = 20;

		// given
		Member member = memberFactory.createMember();

		// when
		for (int i = 1; i <= NUM; i++) {
			Notification noti = notificationService.createNotification(
				member, NotiDetail.builder().message("알림 " + i).destination("?").build()
			);
		}

		long totalElements = notificationService.getNotifications(
			member.getId(), PageRequest.of(0, NUM / 2)
		).getTotalElements();

		// then
		Assertions.assertThat(totalElements).isEqualTo(notificationRepository.findByMemberOrderByCreatedAtDesc(member).size());
	}

}
