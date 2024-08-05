package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
@Entity
public class EventInformation
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long eventid;
	@Email
	private String email;
	@Pattern(regexp = "^[7-9]{1}\\d{9}$", message = "phone number must have 10 digits")
	private String poc;
	@NotEmpty
	private String festtype;
	@NotEmpty
	private String eventname;
	@NotEmpty
	private String address;
	@Temporal(TemporalType.DATE)
	private Date startdate;
	@Temporal(TemporalType.DATE)
	private Date enddate;
	@Temporal(TemporalType.DATE)
	private Date lastdateforregistration;
	@Column(columnDefinition="TEXT")
	@NotEmpty
	private String description;
	private double registrationfees;
	@NotEmpty
	@URL
	private String registrationlink;
	private String status;
	@ManyToOne(cascade = CascadeType.MERGE )
	@JsonBackReference
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "einformation")
    @JsonIgnore
	private List <UpdateRequest> urequest;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "einformation")
    @JsonIgnore
	private List <Feedback> feedback;
	

}
