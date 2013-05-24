package com.insanedev.web;

import javax.annotation.PostConstruct;
import javax.validation.Valid

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebBindingInitializer;

import com.insanedev.Address
import com.insanedev.User;
import com.insanedev.repositories.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id")
	}
	
	@RequestMapping(method=RequestMethod.GET)
	String list(Model model) {
		List<User> users = (List<User>) userRepository.findAll();
		
		model.addAttribute("users", users);
		
		return "user/list";
	}
	
	@RequestMapping(value="/{user}", method=RequestMethod.GET)
	String view(User user, Model model) {
		model.addAttribute("user", user);
		user.addresses.each {
			println it
		}
		
		return "user/view";
	}
	
	@RequestMapping(value = "/{user}/edit", method = RequestMethod.GET)
	public String createForm(User user, Model model) {
		
		model.addAttribute("user", user);
		
		return "user/edit";
	}
	
	@RequestMapping(value = "/{user}/edit", method = RequestMethod.POST)
	public String postForm(@Valid User user, Model model, BindingResult results) {
		
		if (results.hasErrors()) {
			return "user/edit";
		}
		
		userRepository.save(user);
		
		return "redirect:/mvc/user";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createNewForm(Model model) {
		
		User user = new User();
		
		model.addAttribute("user", user);
		
		return "user/edit";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String postNewForm(@Valid User user, Model model, BindingResult results) {
		
		if (results.hasErrors()) {
			return "user/edit";
		}
		
		userRepository.save(user);
		
		return "redirect:/mvc/user";
	}
	
	@RequestMapping(value="/delete", method=[RequestMethod.GET, RequestMethod.DELETE])
	public String delete(User user) {
		userRepository.delete(user);
		
		return "redirect:/mvc/user"
	}

	@PostConstruct
	public void createDefaults() {
		User user = new User(username: 'nlgordon', email: 'nlgordon@gmail.com', firstName: 'Nate', lastName: 'Gordon')
		Address address = new Address(street: '123 Anywhere Lane', city: 'Urbandale', state: 'Iowa', zip: '50322')
		user.addAddress(address)
		address = new Address(street: '2905 Claiborne Circle', city: 'Urbandale', state: 'Iowa', zip: '50322')
		user.addAddress(address)
		userRepository.save(user)
		
		user = new User(username: 'theoneandonlyliz', email: 'theoneandonlyliz@gmail.com', firstName: 'Liz', lastName: 'Gordon')
		address = new Address(street: '123 Anywhere Lane', city: 'Urbandale', state: 'Iowa', zip: '50322')
		user.addAddress(address)
		address = new Address(street: '2905 Claiborne Circle', city: 'Urbandale', state: 'Iowa', zip: '50322')
		user.addAddress(address)
		userRepository.save(user);
		
		user = new User(username: 'joeuser', email: 'joeuser@gmail.com', firstName: 'Joe', lastName: 'User')
		address = new Address(street: '123 Anywhere Lane', city: 'Urbandale', state: 'Iowa', zip: '50322')
		user.addAddress(address)
		address = new Address(street: '2905 Claiborne Circle', city: 'Urbandale', state: 'Iowa', zip: '50322')
		user.addAddress(address)
		userRepository.save(user);
	}
}
