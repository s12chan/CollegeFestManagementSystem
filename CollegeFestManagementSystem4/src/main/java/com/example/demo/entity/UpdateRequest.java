package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class UpdateRequest 
{
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private long uid;
private long eventid;
@Email
private String username;
@NotEmpty
private String udiscription;
private String updatestatus;

@ManyToOne(cascade = CascadeType.MERGE )
@JsonBackReference
private EventInformation einformation;


}
