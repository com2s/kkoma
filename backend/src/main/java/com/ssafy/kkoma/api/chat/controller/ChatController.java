package com.ssafy.kkoma.api.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.chat.dto.request.ChatMessageRequest;
import com.ssafy.kkoma.api.chat.dto.response.ChatMessageResponse;
import com.ssafy.kkoma.api.chat.service.ChatMessageService;
import com.ssafy.kkoma.api.chat.service.ChatRoomService;
import com.ssafy.kkoma.api.product.dto.response.ChatProductResponse;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.global.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final ChatRoomService chatRoomService;
	private final ChatMessageService chatMessageService;
	private final ProductService productService;

	@MessageMapping("/chatRooms/{chatRoomId}")
	public void chat( @DestinationVariable Long chatRoomId, ChatMessageRequest chatMessageRequest) {
		ChatMessageResponse chatMessage = chatMessageService.createChatMessage(chatRoomId, chatMessageRequest);

		simpMessagingTemplate.convertAndSend("/topic/chatRooms/" + chatRoomId, chatMessage);
	}

	@Tag(name = "Chat")
	@Operation(
		summary = "채팅방 별 모든 채팅메시지 조회",
		security = { @SecurityRequirement(name = "bearer-key")}
	)
	@GetMapping("/api/chatRooms/{chatRoomId}")
	public ResponseEntity<ApiUtils.ApiResult<List<ChatMessageResponse>>> getChatMessages(@PathVariable Long chatRoomId) {
		List<ChatMessageResponse> chatMessageResponses = chatRoomService.getChatMessages(chatRoomId);

		return ResponseEntity.ok(ApiUtils.success(chatMessageResponses));
	}

	@Tag(name = "Chat")
	@Operation(
		summary = "채팅방에서 거래 글 정보 조회",
		security = { @SecurityRequirement(name = "bearer-key")}
	)
	@GetMapping("/api/chatRooms/products/{productId}")
	public ResponseEntity<ApiUtils.ApiResult<ChatProductResponse>> getChatProduct(@PathVariable Long productId) {
		return ResponseEntity.ok(ApiUtils.success(productService.getChatProduct(productId)));
	}

}
