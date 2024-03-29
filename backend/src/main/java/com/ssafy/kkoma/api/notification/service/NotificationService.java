package com.ssafy.kkoma.api.notification.service;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.notification.dto.response.NotiDetail;
import com.ssafy.kkoma.api.notification.dto.response.NotificationSummary;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final MemberService memberService;

	public List<NotificationSummary> getNotifications(Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		List<Notification> notiList = notificationRepository.findByMember(member);

		return notiList.stream()
			.map(NotificationSummary::fromEntity)
			.collect(Collectors.toList());
	}

	public Notification createNotification(Member member, NotiDetail notiDetail) {
		return notificationRepository.save(Notification.builder()
			.member(member)
			.message(notiDetail.getMessage())
			.destination(notiDetail.getDestination())
			.sentAt(LocalDateTime.now())
			.build()
		);
	}
}
