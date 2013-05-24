package com.insanedev;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Address extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@NotEmpty
	@Size(min=1, max=50)
	String street;
	
	@NotEmpty
	@Size(min=1, max=50)
	String city;
	
	@NotEmpty
	@Size(min=1, max=20)
	String state;
	
	@NotEmpty
	@Size(min=1, max=5)
	String zip;
	
	@ManyToOne
	User user;

}
