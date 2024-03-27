package com.ssafy.kkoma.api.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.domain.chat.repository.ChatRoomRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	public ChatRoom createChatRoom() {
		return chatRoomRepository.save(new ChatRoom());
	}

	public ChatRoom getChatRoom(Long id) {
		return chatRoomRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHAT_ROOM_NOT_EXISTS));
	}

}
