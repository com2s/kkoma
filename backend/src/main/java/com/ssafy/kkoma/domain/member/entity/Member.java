package com.ssafy.kkoma.domain.member.entity;

import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.location.entity.Location;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.global.jwt.dto.JwtTokenDto;
import com.ssafy.kkoma.global.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Column(length = 20)
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

	@OneToMany(mappedBy = "member")
	private List<Kid> kids = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Product> products = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Offer> offers = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	private Point point;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private Set<WishList> wishes = new HashSet<>();

	private boolean memberInfoCompleted;
	private boolean kidInfoCompleted;

	@Builder
	public Member(MemberType memberType, String email, String name, String profileImage, Role role) {
		this.memberType = memberType;
		this.email = email;
		this.name = name;
		this.profileImage = profileImage;
		this.role = role;
		this.products = new ArrayList<>();
		this.kids = new ArrayList<>();
        memberInfoCompleted = false;
    }

	public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
		this.refreshToken = jwtTokenDto.getRefreshToken();
		this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
	}

	public void expireRefreshToken(LocalDateTime now) {
		this.tokenExpirationTime = now;
	}

	public void updateMemberInfo(UpdateMemberRequest updateMemberRequest) {
		this.profileImage = updateMemberRequest.getProfileImage();
		this.nickname = updateMemberRequest.getNickname();
		this.name = updateMemberRequest.getName();
		this.phone = updateMemberRequest.getPhone();
		this.memberInfoCompleted = true;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void setMemberInfoCompleted(boolean additionalInfoCompleted) {
		this.memberInfoCompleted = additionalInfoCompleted;
	}

	public void setKidInfoCompleted(boolean kidInfoCompleted) {
		this.kidInfoCompleted = kidInfoCompleted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPoint(int value) {
		point.addBalance(value);
	}

	public void subPoint(int value) {
		point.subBalance(value);
	}

}
