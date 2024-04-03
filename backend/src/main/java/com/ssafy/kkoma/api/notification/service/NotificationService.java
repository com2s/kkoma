package com.ssafy.kkoma.api.notification.service;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.notification.dto.response.NotiDetail;
import com.ssafy.kkoma.api.notification.dto.response.NotificationSummary;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.notification.repository.NotificationRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Notification findNotiByNotiId(Long notiId){
		return notificationRepository.findById(notiId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTI_NOT_EXISTS));
	}

	public BasePageResponse<Notification, NotificationSummary> getNotifications(Long memberId, Pageable pageable) {
		Member member = memberService.findMemberByMemberId(memberId);
		Page<Notification> page = notificationRepository.findByMemberOrderByCreatedAtDesc(member, pageable);

		List<NotificationSummary> content = page.getContent().stream()
				.map(NotificationSummary::fromEntity)
				.collect(Collectors.toList());

		return new BasePageResponse<>(content, page);
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

	public Notification readNotification(Long memberId, Long notiId) {
		 Notification noti = findNotiByNotiId(notiId);
		 noti.setReadAt();
		 return noti;
	}

	public Boolean hasUnreadNotis(Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		List<Notification> notiList = notificationRepository.findByMemberAndReadAt(member, null);
		return !notiList.isEmpty();
	}
}
