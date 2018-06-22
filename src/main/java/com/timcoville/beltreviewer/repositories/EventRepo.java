package com.timcoville.beltreviewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.timcoville.beltreviewer.models.Event;

public interface EventRepo extends CrudRepository<Event, Long>{
	@Query("SELECT e.name, e.eventDate, e.city, u.firstName, u.id, e.id FROM User u JOIN u.events e WHERE e.state = ?1")
	List<Object[]> findAllEventsInState(String state);

	
	List<Event> findByState(String state);
	List<Event> findByStateNotIn(String state);
}
