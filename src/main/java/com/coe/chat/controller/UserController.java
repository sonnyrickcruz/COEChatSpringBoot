package com.coe.chat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.coe.chat.domain.User;
import com.coe.chat.socketdomain.UserLogin;

@Controller
public class UserController {
	@MessageMapping("login")
	@SendTo("client/login")
	public String login(UserLogin userLogin) throws InterruptedException {
		Thread.sleep(1000);
		System.out.println(userLogin);
		String result = "FAIL";
		if (userLogin.getUsername() != null && isValidUser(userLogin.getUsername())) {
			result = "SUCCESS";
		}
		System.out.println(result);
		return result;
	}

	private boolean isValidUser(String username) {
		boolean result = false;
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

		for (User userLoop : users) {
			if (username != null && username.equalsIgnoreCase(userLoop.getUsername())) {
				result = true;
			}
		}
		return result;
	}
}
