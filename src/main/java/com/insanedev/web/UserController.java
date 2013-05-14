package com.insanedev.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.insanedev.User;
import com.insanedev.repositories.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	String list(Model model) {
		List<User> users = (List<User>) userRepository.findAll();
		
		model.addAttribute("list", users);
		
		return "user/list";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/view")
	String view(@RequestParam("id") Long id, Model model) {
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		
		return "user/view";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/edit")
	public String createForm(@RequestParam(value = "id", required = false) Long id, Model model) {
		
		User user;
		
		if (id != null) {
			user = userRepository.findOne(id);
		} else {
			user = new User();
		}
		
		model.addAttribute("user", user);
		
		return "user/edit";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit")
	public String postForm(@ModelAttribute("user") User user, Model model, BindingResult results) {
		
//		new UserValidator().validate(user, results);
		
		if (results.hasErrors()) {
			return "user/edit";
		}
		
		userRepository.save(user);
		
		return "redirect:/user";
	}

}
