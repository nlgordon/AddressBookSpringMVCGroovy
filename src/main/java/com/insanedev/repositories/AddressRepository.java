package com.insanedev.repositories;

import org.springframework.data.repository.CrudRepository;

import com.insanedev.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
