package com.noder.ocppserver.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(ocppHandler(), "/ocpp/{chargerId}")
			.setAllowedOrigins("*")
			.setHandshakeHandler(new OcppHandshakeHandler(new DefaultHandshakeHandler()));
	}

	@Bean
	public WebSocketHandler ocppHandler() {
		return new OcppHandler();
	}
}
