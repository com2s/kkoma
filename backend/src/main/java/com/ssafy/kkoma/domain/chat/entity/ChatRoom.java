package com.ssafy.kkoma.domain.chat.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
public class ChatRoom extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "chatRoom")
	private List<ChatMessage> chatMessageList = new ArrayList<>();

	@Setter
	@OneToOne(mappedBy = "chatRoom")
	private Product product;
}
