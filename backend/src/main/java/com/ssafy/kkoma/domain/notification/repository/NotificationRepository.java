package com.ssafy.kkoma.domain.notification.repository;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByMemberOrderByCreatedAtDesc(Member member);

	Page<Notification> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);

	List<Notification> findByMemberAndReadAt(Member member, LocalDateTime readAt);

	List<Notification> findByDestination(String destination);

}
