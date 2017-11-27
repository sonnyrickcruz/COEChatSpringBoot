package com.coe.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;

import com.coe.chat.domain.Message;
import com.coe.chat.domain.User;
import com.coe.chat.socketdomain.UserLogin;

@Controller
public class UserController {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	private static Map<String, String> sessionMap = new HashMap<>();

	@MessageMapping("login")
	public void login(UserLogin userLogin, SimpMessageHeaderAccessor accessor) throws InterruptedException {
		Thread.sleep(1000);
		System.out.println(accessor.getSessionId());
		if (sessionMap.get(userLogin.getUsername()) == null) {
			sessionMap.put(userLogin.getUsername(), accessor.getSessionId());
			System.out.println(sessionMap);
		}
		if (userLogin.getUsername() != null) {
			messagingTemplate.convertAndSendToUser(sessionMap.get(userLogin.getUsername()), "/client/login",
					getUser(userLogin.getUsername()), createHeaders(sessionMap.get(userLogin.getUsername())));
		}
	}

	@MessageMapping("message")
	public void sendMessage(Message message, SimpMessageHeaderAccessor accessor) throws InterruptedException {
		Thread.sleep(1000);
		System.out.println(message.getReceiver());
		System.out.println(sessionMap);
		String recieverSessionId = sessionMap.get(message.getReceiver());
		System.out.println(recieverSessionId);
		if (recieverSessionId != null) {
			messagingTemplate.convertAndSendToUser(recieverSessionId, "/queue/message", message,
					createHeaders(recieverSessionId));
		}
	}

	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}

	private User getUser(String username) {
		List<User> users = new ArrayList<>();
		User user;

		user = new User();
		user.setUsername("user1");
		user.setFirstName("Rick");
		user.setLastName("Cruz");
		user.setGender("M");
		users.add(user);

		user = new User();
		user.setUsername("user2");
		user.setFirstName("test");
		user.setLastName("test");
		user.setGender("F");
		users.add(user);

		user = new User();
		user.setUsername("user3");
		user.setFirstName("test");
		user.setLastName("test");
		user.setGender("F");
		users.add(user);
		


		user = new User();
		user.setUsername("user4");
		user.setFirstName("test");
		user.setLastName("test");
		user.setGender("F");
		users.add(user);

		for (User userLoop : users) {
			if (username != null && username.equalsIgnoreCase(userLoop.getUsername())) {
				return userLoop;
			}
		}
		return null;
	}
}
