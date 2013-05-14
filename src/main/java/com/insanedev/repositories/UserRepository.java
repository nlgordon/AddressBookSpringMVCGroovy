package com.insanedev.repositories;

import org.springframework.data.repository.CrudRepository;

import com.insanedev.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
