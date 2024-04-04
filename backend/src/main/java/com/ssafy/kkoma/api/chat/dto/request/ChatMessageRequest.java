package com.ssafy.kkoma.api.chat.dto.request;

import lombok.Getter;

@Getter
public class ChatMessageRequest {

	private Long memberId;
	private String content;

}
