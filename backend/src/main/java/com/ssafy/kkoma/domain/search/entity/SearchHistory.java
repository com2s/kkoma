package com.ssafy.kkoma.domain.search.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class SearchHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(length = 100)
	private String searchWord;

}
