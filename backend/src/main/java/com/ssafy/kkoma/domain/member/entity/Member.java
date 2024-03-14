package com.ssafy.kkoma.domain.member.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.location.entity.Location;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(length = 50)
	private String email;

	private String profileImage;

	@Column(length = 50)
	private String name;

	@Column(length = 50)
	private String nickname;

	@Column(length = 11)
	private String phone;

	@Column
	private boolean isValid = true;

	private Long replyCount;

	private Long replyTotalTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "member_type", nullable = false, length = 10)
	private MemberType memberType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Role role;

	@Column(length = 250)
	private String refreshToken;

	private LocalDateTime tokenExpirationTime;
}
