package com.coe.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;

import com.coe.chat.domain.User;
import com.coe.chat.socketdomain.UserLogin;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	public static Map<String, String> sessionMap = new HashMap<>();

	private static List<User> users = new ArrayList<>();

	@MessageMapping("login")
	public void login(UserLogin userLogin, SimpMessageHeaderAccessor accessor) throws InterruptedException {
		Thread.sleep(700);
		User user = getUser(userLogin.getUsername());
		System.out.println("SessionId: " + accessor.getSessionId());
		System.out.println("SessionId2: " + accessor.getSessionAttributes().get("sessionId").toString());
		String sessionId = accessor.getSessionId();
		accessor.getSessionAttributes().get("sessionId").toString();
		if (user != null) {
			if (sessionMap.get(userLogin.getUsername()) == null) {
				sessionMap.put(userLogin.getUsername(), sessionId);
				System.out.println(sessionMap);
			}
			if (userLogin.getUsername() != null) {
				messagingTemplate.convertAndSendToUser(sessionMap.get(userLogin.getUsername()), "/client/login",
						getUser(userLogin.getUsername()), createHeaders(sessionMap.get(userLogin.getUsername())));
			}
		}
	}

	@MessageMapping("online")
	public void online() throws InterruptedException {
		Thread.sleep(1000);
		// Send to all users, did not use the other way to handle friends on the future.
		System.out.print("Users from session");
		List<User> users = new ArrayList<>();
		for (String user2 : sessionMap.keySet()) {
			users.add(getUser(user2));
		}
		if (sessionMap.keySet().size() > 0) {
			for (String userName : sessionMap.keySet()) {
				if (userName != null) {
					System.out.print(userName);
					messagingTemplate.convertAndSendToUser(sessionMap.get(userName), "/client/online", users,
							createHeaders(sessionMap.get(userName)));
				}
				System.out.println();
			}
		}
	}

	public static MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}
	
	public static void removeFromOnline(String sessionId) {
		String sId;
		for (String user : sessionMap.keySet()) {
			sId = sessionMap.get(user);
			if (sId != null && sId.equalsIgnoreCase(sessionId)) {
				sessionMap.remove(user);
				logger.debug(user + " disconnected");
				break;
			}
		}
	}

	public static User getUser(String username) {
		users = new ArrayList<>();
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
		

		user = new User();
		user.setUsername("a");
		user.setFirstName("atest");
		user.setLastName("atest");
		user.setGender("F");
		users.add(user);
		
		user = new User();
		user.setUsername("b");
		user.setFirstName("btest");
		user.setLastName("btest");
		user.setGender("F");
		users.add(user);
		
		user = new User();
		user.setUsername("c");
		user.setFirstName("ctest");
		user.setLastName("ctest");
		user.setGender("F");
		users.add(user);
		
		user = new User();
		user.setUsername("d");
		user.setFirstName("ctest");
		user.setLastName("ctest");
		user.setGender("F");
		users.add(user);
		
		user = new User();
		user.setUsername("e");
		user.setFirstName("ctest");
		user.setLastName("ctest");
		user.setGender("F");
		users.add(user);
		
		user = new User();
		user.setUsername("f");
		user.setFirstName("ctest");
		user.setLastName("ctest");
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
