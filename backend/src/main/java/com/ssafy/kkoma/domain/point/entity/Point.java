package com.ssafy.kkoma.domain.point.entity;

import com.ssafy.kkoma.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Point {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int balance = 0;

	public void addBalance(int value) {
		this.balance += value;
	}

	public void subBalance(int value) {
		this.balance -= value;
	}

}
