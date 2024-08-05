package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.UserNotFoundException;
import com.example.demo.entity.EventInformation;
import com.example.demo.repository.EventInformationRepository;
import com.example.demo.utils.MessageResponse;
@RestController
public class AdminController 
{
	@Autowired
	EventInformationRepository erepo;
	
	@Value("${spring.mail.username}")
	private String fromemail;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@GetMapping("admin/showallevents") //To get all the events of all the registered users.
	public List<EventInformation> showalleventdetails()
	{
		return erepo.findAll();
	}
	
	
	@GetMapping("admin/showallunverifiedevents")//To get all the unverified events
	public ResponseEntity<List<EventInformation>> showAllUnVerifiedEvents()
	{
		String status="Not verified";
	return new ResponseEntity<>(erepo.findByStatus(status),HttpStatus.OK);
	}
	
	
	@PutMapping("admin/updateeventstatus/{eventid}")//To verify the event details added by user
	   public ResponseEntity<Object> updateverficationbyadmin(@PathVariable long eventid,@RequestBody EventInformation event)
	{
		   
	       EventInformation updateevent = erepo.findById(eventid)
	    			     .orElseThrow(() -> new UserNotFoundException("Event does not not exist with this id: " + eventid));

	       String email=updateevent.getEmail();
	       updateevent.setStatus(event.getStatus());
	       erepo.save(updateevent);
	       
	       SimpleMailMessage mailMessage= new SimpleMailMessage();
	   	   mailMessage.setFrom(fromemail);
	   	   mailMessage.setTo(email);
	   	   mailMessage.setText("The event with id "+updateevent.getEventid()+" is "+updateevent.getStatus());
	   	   mailMessage.setSubject("Event Confirmation Status");
	   	   javaMailSender.send(mailMessage);
	            return ResponseEntity.ok(updateevent);
	   }

	
	@DeleteMapping("admin/deleteevent/{eventid}")//To delete registered user's event
	public ResponseEntity<Object> deleteevent(@PathVariable long eventid)
	{
	Optional<EventInformation> eobj=erepo.findById(eventid);
	if(!eobj.isPresent())
	{
		return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("The event ID does no exist"));
	}
	erepo.deleteById(eventid);
	return ResponseEntity.ok("The event is deleted");
	}
	
	
	
	



}
