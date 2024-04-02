package com.ssafy.kkoma.api.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public ChatMessageResponse createChatMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest) {
		Member member = memberService.findMemberByMemberId(chatMessageRequest.getMemberId());
		ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);

		ChatMessage chatMessage = ChatMessage.builder()
			.content(chatMessageRequest.getContent())
			.member(member)
			.build();

		chatMessage.setChatRoom(chatRoom);

		return ChatMessageResponse.fromEntity(chatMessageRepository.save(chatMessage));
	}

	@Transactional
	public void createChatMessage(Long chatRoomId, Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);

		ChatMessage chatMessage = ChatMessage.builder()
			.content("거래가 성사되었습니다")
			.member(member)
			.build();

		chatMessage.setChatRoom(chatRoom);
	}

}
