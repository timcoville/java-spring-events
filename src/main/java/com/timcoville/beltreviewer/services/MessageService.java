package com.timcoville.beltreviewer.services;

import org.springframework.stereotype.Service;

import com.timcoville.beltreviewer.models.Message;
import com.timcoville.beltreviewer.repositories.MessageRepo;

@Service
public class MessageService {
	private final MessageRepo messageRepo;
	public MessageService(MessageRepo messageRepo) {
		this.messageRepo = messageRepo;
	}
	public Message createMessage(Message message) {
		return messageRepo.save(message);
	}
}
