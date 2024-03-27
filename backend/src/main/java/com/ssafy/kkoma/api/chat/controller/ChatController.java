package com.ssafy.kkoma.api.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.chat.dto.request.ChatMessageRequest;
import com.ssafy.kkoma.api.chat.dto.response.ChatMessageResponse;
import com.ssafy.kkoma.api.chat.service.ChatMessageService;

import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final ChatMessageService chatMessageService;

	@MessageMapping("/chatRooms/{chatRoomId}")
	public void chat( @DestinationVariable Long chatRoomId, ChatMessageRequest chatMessageRequest) {
		ChatMessageResponse chatMessage = chatMessageService.createChatMessage(chatRoomId, chatMessageRequest);

		simpMessagingTemplate.convertAndSend("/topic/chatRooms/" + chatRoomId, chatMessage);
	}

}
