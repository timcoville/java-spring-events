package com.timcoville.beltreviewer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.timcoville.beltreviewer.models.Message;

public interface MessageRepo extends CrudRepository<Message, Long> {

}
