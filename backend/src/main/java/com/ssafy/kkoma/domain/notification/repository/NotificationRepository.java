package com.ssafy.kkoma.domain.notification.repository;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByMember(Member member);

	Page<Notification> findByMember(Member member, Pageable pageable);

}
