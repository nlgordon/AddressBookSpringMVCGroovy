package com.insanedev.web.util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.insanedev.User;
import com.insanedev.repositories.UserRepository;

@Component
public class StringToUserConverter implements Converter<String, User>{
	
	@Autowired
	UserRepository userRepository;

	public User convert(String source) {
		return userRepository.findOne(Long.parseLong(source));
	}

}
