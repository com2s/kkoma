package com.ssafy.kkoma.api.chat.service;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.api.chat.dto.request.ChatMessageRequest;
import com.ssafy.kkoma.api.chat.dto.response.ChatMessageResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.chat.entity.ChatMessage;
import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.domain.chat.repository.ChatMessageRepository;
import com.ssafy.kkoma.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

	private final ChatMessageRepository chatMessageRepository;
	private final MemberService memberService;
	private final ChatRoomService chatRoomService;

	public ChatMessageResponse createChatMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest) {
		Member member = memberService.findMemberByMemberId(chatMessageRequest.getMemberId());
		ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);

		ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder()
			.content(chatMessageRequest.getContent())
			.member(member)
			.chatRoom(chatRoom)
			.build());

		return ChatMessageResponse.fromEntity(chatMessage);
	}

}
