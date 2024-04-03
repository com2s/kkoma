package com.ssafy.kkoma.api.chat.dto.response;

import java.time.format.DateTimeFormatter;

import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.domain.chat.entity.ChatMessage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponse {

	private Long id;
	private MemberProfileResponse memberProfile;
	private String content;
	private String sentTime;

	public static ChatMessageResponse fromEntity(ChatMessage chatMessage) {
		return ChatMessageResponse.builder()
			.id(chatMessage.getId())
			.memberProfile(MemberProfileResponse.fromEntity(chatMessage.getMember()))
			.content(chatMessage.getContent())
			.sentTime(chatMessage.getCreatedAt().format(DateTimeFormatter.ofPattern("MM/dd HH:mm")))
			.build();
	}

}
