package com.ssafy.kkoma.domain.chat.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(columnDefinition = "text")
	private String content;

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		chatRoom.getChatMessageList().add(this);
	}

}
