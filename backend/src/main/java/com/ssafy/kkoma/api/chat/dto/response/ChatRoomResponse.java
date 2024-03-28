package com.ssafy.kkoma.api.chat.dto.response;

import java.util.List;

import com.ssafy.kkoma.api.product.dto.response.ChatProductResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomResponse {

	private ChatProductResponse chatProductResponse;
	private List<ChatMessageResponse> chatMessageResponseList;

}
