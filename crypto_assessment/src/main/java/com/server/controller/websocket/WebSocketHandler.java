//package com.crypto.controller.websocket;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public WebSocketHandler(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Handle incoming messages
//        String payload = message.getPayload();
//
//        // Assuming the payload contains a client request
//        // Process the request and send a response
//        String responsePayload = "Response to: " + payload;
//        messagingTemplate.convertAndSendToUser(session.getId(), "/queue/responseQueue", responsePayload);
//    }
//
//    @Scheduled(fixedRate = 1000) // Send message every second
//    public void sendPeriodicMessage() {
//        // Send a periodic message to all connected clients
//        String periodicMessage = "This is a periodic message!";
//        messagingTemplate.convertAndSend("/topic/periodicTopic", periodicMessage);
//    }
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // Handle a new connection
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // Handle connection closure
//    }
//}
