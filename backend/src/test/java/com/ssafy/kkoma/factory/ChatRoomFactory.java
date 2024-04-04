package com.ssafy.kkoma.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.domain.chat.repository.ChatRoomRepository;

@Component
public class ChatRoomFactory {

	@Autowired
	ChatRoomRepository chatRoomRepository;

	public ChatRoom createChatRoom() {
		return chatRoomRepository.save(new ChatRoom());
	}

}
