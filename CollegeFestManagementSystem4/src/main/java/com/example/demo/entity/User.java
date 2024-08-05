package com.example.demo.entity;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class User 
{
	@Id
	@Email
	private String username;
	@NotEmpty
	@Pattern(regexp="^[A-Za-z]+$")
	@Size(min=5,max=20)
	private String name;
	@NotEmpty
	@Size(min=8,max=120)
	private String password;
	@Pattern(regexp = "^[2-9]{2}\\d{8}$", message = "phone number must have 10 digits")
	private String mobilenumber;
	@NotEmpty
	private String role;
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "user")
	@JsonBackReference
	private List <EventInformation> events;
}
