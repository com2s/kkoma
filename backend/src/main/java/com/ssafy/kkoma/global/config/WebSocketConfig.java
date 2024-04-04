package com.ssafy.kkoma.global.config;

import com.ssafy.kkoma.global.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/api/chat")
			.setAllowedOriginPatterns("*")
			.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메시지 구독 요청 url -> 메시지 받을 때
		registry.enableSimpleBroker("/topic");
		// 메시지 발행 요청 url -> 메시지 보낼 때
		registry.setApplicationDestinationPrefixes("/app");
	}

}
