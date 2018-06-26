package com.timcoville.beltreviewer.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.timcoville.beltreviewer.models.Event;
import com.timcoville.beltreviewer.models.Message;
import com.timcoville.beltreviewer.models.User;
import com.timcoville.beltreviewer.services.EventService;
import com.timcoville.beltreviewer.services.MessageService;
import com.timcoville.beltreviewer.services.UserService;

@Controller
public class EventsController {
	private final EventService eventService;
	private final UserService userService;
	private final MessageService messageService;
	public EventsController(EventService eventService, UserService userService, MessageService messageService) {
		this.eventService = eventService;
		this.userService = userService;
		this.messageService = messageService;
	}
	
	@GetMapping("/events")
	public String events(Model model, HttpSession session, @ModelAttribute("event") Event event) {
		
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		} 
		
		User user = userService.findUserById((Long) session.getAttribute("id"));
		List<Event> userEvents = eventService.findAllEvents(user.getState());
		List<Event> userEvents2 = eventService.findAllEventsOut(user.getState());
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String tempDate = dateFormat.format(date);
		
		model.addAttribute("date", tempDate);
		model.addAttribute("user", user);
		model.addAttribute("userEvents", userEvents);
		model.addAttribute("userEvents2", userEvents2);
		
		return "/events.jsp";
		
	}
	
	@PostMapping("/events")
	public String createEvent(@Valid @ModelAttribute("event") Event event, @RequestParam("newEventDate") String eventDate, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
		
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		
		if (result.hasErrors()) {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			String tempDate = dateFormat.format(date);
			model.addAttribute("date", tempDate);
			return "/events.jsp";
		} else {
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date tempDate = format.parse(eventDate);
				event.setEventDate(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			User user = userService.findUserById((Long) session.getAttribute("id"));
			event.setUser(user);
			List<User> attendees = new ArrayList<User>();
			attendees.add(user);
			System.out.println(attendees);
			event.setAttendees(attendees);
			eventService.createEvent(event);
			return "redirect:/events";
		}
	
	}
	
	@GetMapping("/events/{id}")
	public String viewEvent(@PathVariable("id")Long id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Event event = eventService.findEvent(id);
		if (event == null) {
			return "redirect:/events";
		} else {
			User user = userService.findUserById((Long) session.getAttribute("id"));
			model.addAttribute("user", user);
			model.addAttribute("event", event);
			return "viewEvent.jsp";
		}
	}
	
	@PostMapping("/messages")
	public String createMessage(@RequestParam("message")String message, @RequestParam("eventID")Long idEvent, @RequestParam("userID")Long idUser, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Event event = eventService.findEvent(idEvent);
		User user = userService.findUserById(idUser);
		Message newMessage = new Message();
		newMessage.setMessage(message);
		newMessage.setUser(user);
		newMessage.setEvent(event);
		messageService.createMessage(newMessage);
		return "redirect:/events/"+event.getId();
	
	}
	
	@GetMapping("/join/{userID}/{eventID}")
	public String joinEvent(@PathVariable("userID")Long userID, @PathVariable("eventID") Long eventID, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Event event = eventService.findEvent(eventID);
		User user = userService.findUserById(userID);
		List<User> attendees = event.getAttendees();
		attendees.add(user);
		event.setAttendees(attendees);
		eventService.createEvent(event);
		return "redirect:/events";
		
	}
	
	@GetMapping("/cancel/{userID}/{eventID}")
	public String cancelEvent(@PathVariable("userID")Long userID, @PathVariable("eventID") Long eventID, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Event event = eventService.findEvent(eventID);
		User user = userService.findUserById(userID);
		List<User> attendees = event.getAttendees();
		attendees.remove(attendees.indexOf(user));
		event.setAttendees(attendees);
		eventService.createEvent(event);
		return "redirect:/events";
		
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEvent(@PathVariable("id")Long id, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		eventService.deleteEvent(id);
		return "redirect:/events";
	}
	
	@GetMapping("/edit/{id}")
	public String editEvent(@PathVariable("id")Long id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Event event = eventService.findEvent(id);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String tempDate = dateFormat.format(date);
		String currDate = dateFormat.format(event.getEventDate());
		model.addAttribute("date", tempDate);
		model.addAttribute("currDate", currDate);
		model.addAttribute("event", event);
		return "/editEvent.jsp";
	}
	
	@PutMapping("/edit/{id}")
	public String updateEvent(@Valid @ModelAttribute("event")Event event, BindingResult result, @RequestParam("newEventDate")String eventDate, @PathVariable("id")Long id, HttpSession session, Model model) {
		if (result.hasErrors()) {
			Event record = eventService.findEvent(id);
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			String tempDate = dateFormat.format(date);
			String currDate = dateFormat.format(record.getEventDate());
			model.addAttribute("date", tempDate);
			model.addAttribute("currDate", currDate);
			
			System.out.println("ERROR ON PUT");
			return "/editEvent.jsp";
		} else {
		
			Event record = eventService.findEvent(id);
			
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date tempDate = format.parse(eventDate);
				event.setEventDate(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			record.setName(event.getName());
			record.setEventDate(event.getEventDate());
			record.setCity(event.getCity());
			record.setState(event.getState());
			eventService.createEvent(record);
			return "redirect:/events";
		}
	}
	
	
	
	
	
	
	
	
	
	
}
