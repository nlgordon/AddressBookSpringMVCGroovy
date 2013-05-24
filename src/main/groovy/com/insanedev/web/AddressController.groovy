package com.insanedev.web

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.insanedev.Address
import com.insanedev.User
import com.insanedev.repositories.AddressRepository;

@Controller
class AddressController {
	
	@Autowired
	AddressRepository addressRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id")
	}
	
	@RequestMapping(value="/user/{user}/address/new", method=RequestMethod.GET)
	public String getNewForm(User user, Model model) {
		Address address = new Address()
		user.addAddress(address)
		
		model.addAttribute("address", address)
		return "user/address/edit"
	}
	
	@RequestMapping(value="/user/{user}/address/new", method=[RequestMethod.POST, RequestMethod.PUT])
	public String postNewForm(User user, @Valid Address address, Model model, BindingResult results) {
		user.addAddress(address)
		
		if (results.hasErrors()) {
			return "user/address/edit";
		}
		
		addressRepository.save(address)
		
		return "redirect:/mvc/user/" + user.id
	}
	
	@RequestMapping(value="/user/{user}/address/{address}/edit", method=RequestMethod.GET)
	public String getEditForm(User user, Address address ,Model model) {
		model.addAttribute("address", address)
		return "user/address/edit"
	}
	
	@RequestMapping(value="/user/{user}/address/{address}/edit", method=[RequestMethod.POST, RequestMethod.PUT])
	public String postEditForm(User user, @Valid Address address, Model model, BindingResult results) {
		user.addAddress(address)
		
		if (results.hasErrors()) {
			return "user/address/edit";
		}
		
		addressRepository.save(address)
		
		return "redirect:/mvc/user/" + user.id
	}
	
	@RequestMapping(value="/user/{user}/address/{address}/delete", method=[RequestMethod.GET, RequestMethod.DELETE])
	public String delete(Address address) {
		addressRepository.delete(address)
		
		return "redirect:/mvc/user/{user}"
	}
}
