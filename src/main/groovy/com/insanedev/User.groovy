package com.insanedev;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@NotEmpty
	@Size(min=1, max=20)
	String username;
	
	@NotEmpty
	@Size(min=1, max=100)
	String email;
	
	@NotEmpty
	@Size(min=1, max=100)
	String firstName;
	
	@NotEmpty
	@Size(min=1, max=100)
	String lastName;

	@OneToMany(mappedBy="user")
	List<Address> addresses = new ArrayList<Address>();
	
	public void addAddress(Address address) {
		this.addresses.add(address)
		address.setUser(this)
	}
}
