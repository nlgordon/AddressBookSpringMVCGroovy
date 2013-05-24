package com.insanedev.web.util;

import com.insanedev.Address;
import com.insanedev.repositories.AddressRepository
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAddressConverter  implements Converter<String, Address>{
	
	@Autowired
	AddressRepository addressRepository

	@Override
	public Address convert(String source) {
		return addressRepository.findOne(Long.parseLong(source))
	}

}
