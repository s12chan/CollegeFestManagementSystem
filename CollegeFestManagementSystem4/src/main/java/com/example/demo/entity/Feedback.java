package com.example.demo.entity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class Feedback 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long fid;
	@Email
	@NotEmpty
    String email;
	Long eventid;
	@Max(value=10)
	@Min(value=1)
	int q1;
	@Max(value=10)
	@Min(value=1)
	int q2;
	@Max(value=10)
	@Min(value=1)
	int q3;
	
	@ManyToOne(cascade = CascadeType.MERGE )
	@JsonBackReference
	private EventInformation einformation;
	

}
