package com.timcoville.beltreviewer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.timcoville.beltreviewer.models.Event;
import com.timcoville.beltreviewer.repositories.EventRepo;

@Service
public class EventService {
	private final EventRepo eventRepo;
	public EventService(EventRepo eventRepo) {
		this.eventRepo = eventRepo;
	}
	
	public List<Object[]> allEvents(String state){
		return eventRepo.findAllEventsInState(state);
	}
	
	public Event createEvent(Event event) {
		return eventRepo.save(event);
	}
	
	public Event findEvent(Long id) {
		Optional<Event> record = eventRepo.findById(id);
		if(record.isPresent()) {
			return record.get();
		} else {
			return null;
		}
	}
	

	
	public List<Event> findAllEvents(String state){
		return eventRepo.findByState(state);
		
	}
	
	public List<Event> findAllEventsOut(String state){
		return eventRepo.findByStateNotIn(state);
	}
	
	public void deleteEvent(Long id) {
		Event event = findEvent(id);
		eventRepo.delete(event);
		return;
	}
	
}
