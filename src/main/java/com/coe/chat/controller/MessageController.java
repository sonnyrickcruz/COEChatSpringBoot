package com.coe.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.coe.chat.domain.Message;

@Controller
public class MessageController {
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@MessageMapping("message")
	public void sendMessage(Message message, SimpMessageHeaderAccessor accessor) throws InterruptedException {
		Thread.sleep(1000);
		System.out.println(message.getReceiver());
		System.out.println(UserController.sessionMap);
		String recieverSessionId = UserController.sessionMap.get(message.getReceiver());
		System.out.println(recieverSessionId);
		if (recieverSessionId != null) {
			messagingTemplate.convertAndSendToUser(recieverSessionId, "/queue/message", message,
					UserController.createHeaders(recieverSessionId));
		}
	}


}
