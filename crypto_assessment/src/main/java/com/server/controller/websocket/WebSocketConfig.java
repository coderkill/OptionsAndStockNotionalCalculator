//package com.crypto.controller.websocket;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//@EnableScheduling // Enable scheduled tasks
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Bean
//    public WebSocketHandler myWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
//        return new WebSocketHandler(messagingTemplate);
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myWebSocketHandler(null), "/ws").setAllowedOrigins("*");
//        // Note: Pass 'null' as the parameter for now, it will be injected by Spring
//    }
//
//    @Bean
//    public SimpMessagingTemplate messagingTemplate() {
//        return new SimpMessagingTemplate(/* your message broker, e.g., a SimpleBrokerMessageHandler */);
//    }
//}
