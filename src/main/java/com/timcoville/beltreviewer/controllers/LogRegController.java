package com.timcoville.beltreviewer.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.timcoville.beltreviewer.models.User;
import com.timcoville.beltreviewer.services.UserService;

@Controller
public class LogRegController {
	
	private final UserService userService;
	public LogRegController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String index(@ModelAttribute("user")User user) {
		return "logReg.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("user")User user, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes){
		if (result.hasErrors()) {
			System.out.println("ERROR");
			return "logReg.jsp";
		} else {
			Boolean auth = userService.authenticateUser(user.getEmail(), user.getPassword());
			if (auth) {
				User record = userService.findByEmail(user.getEmail());
				session.setAttribute("id", record.getId());
				return "redirect:/events";
			} else {
				redirectAttributes.addFlashAttribute("loginError", "Login Credentials Failed");
				return "redirect:/";
			}
		}
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user")User user, BindingResult result, HttpSession session){
		if (result.hasErrors()) {
			return "logReg.jsp";
		} else {
			User record = userService.registerUser(user);
			session.setAttribute("id", record.getId());
			return "redirect:/events";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	
}
