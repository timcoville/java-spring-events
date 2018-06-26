package com.timcoville.beltreviewer.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.timcoville.beltreviewer.models.User;
import com.timcoville.beltreviewer.services.UserService;
import com.timcoville.beltreviewer.validators.UserValidator;

@Controller
public class LogRegController {
	
	private final UserService userService;
	private final UserValidator userValidator;
	public LogRegController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping("/")
	public String index(@ModelAttribute("user")User user) {
		return "logReg.jsp";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes){

		Boolean auth = userService.authenticateUser(email, password);
		if (auth) {
			User record = userService.findByEmail(email);
			session.setAttribute("id", record.getId());
			return "redirect:/events";
		} else {
			redirectAttributes.addFlashAttribute("loginError", "Login Credentials Failed");
			return "redirect:/";
		}

	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user")User user, BindingResult result, HttpSession session){
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			user.setEmail("");
			user.setFirstName("");
			user.setLastName("");
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
