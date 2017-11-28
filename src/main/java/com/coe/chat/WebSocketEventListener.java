package com.coe.chat;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.coe.chat.controller.UserController;
import com.coe.chat.domain.User;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        logger.info("User Disconnected: " + headerAccessor.getSessionId());
        UserController.removeFromOnline(headerAccessor.getSessionId());
        
		List<User> users = new ArrayList<>();
		for (String user2 : UserController.sessionMap.keySet()) {
			users.add(UserController.getUser(user2));
		}
		if (UserController.sessionMap.keySet().size() > 0) {
			for (String userName : UserController.sessionMap.keySet()) {
				if (userName != null) {
					System.out.print(userName);
					messagingTemplate.convertAndSendToUser(UserController.sessionMap.get(userName), "/client/online", users,
							UserController.createHeaders(UserController.sessionMap.get(userName)));
				}
				System.out.println();
			}
		}
    }
}