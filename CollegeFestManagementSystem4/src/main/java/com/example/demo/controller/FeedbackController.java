package com.example.demo.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.EventNotFoundException;
import com.example.demo.entity.EventInformation;
import com.example.demo.entity.Feedback;
import com.example.demo.repository.EventInformationRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.utils.MessageResponse;

@RestController
public class FeedbackController 
{
	@Autowired
	FeedbackRepository frepo;
	
	@Autowired
	EventInformationRepository erepo;
	
	@Value("${spring.mail.username}")
	private String fromemail;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@GetMapping("/showallfeedback")
	public List<Feedback> showallfeedback()
	{
	return frepo.findAll();
	}
	
	@GetMapping("/viewefeedbackbyeventid/{eventid}")//To get events based on location
	public ResponseEntity<List<Feedback>> showfeedbackbyeventid(@PathVariable("eventid") long eventid)
	{
	return new ResponseEntity<>(frepo.findByEventid(eventid),HttpStatus.OK);
	}
	
	@PostMapping("/addfeedback")//Add the Feedback
	public ResponseEntity<Object> addfeedback(@Valid @RequestBody Feedback fdb)
	{
	long eventid=fdb.getEventid();
	String email=fdb.getEmail();
	Date date = new Date();
	EventInformation eobj=erepo.findById(eventid)
			.orElseThrow(() -> new EventNotFoundException("Event does not not exist with this ID: " + eventid));
	fdb.setEinformation(eobj);
	List<EventInformation> eventslist=erepo.findAll();
	
	boolean result1=eventslist.stream().anyMatch(p->(p.getEventid()==eventid && p.getEnddate().after(date)));
	if(result1)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			    .body(new MessageResponse("The event is not yet completed...."));	
	}
	List<Feedback> feedbacklist=frepo.findAll();
	boolean result=feedbacklist.stream().anyMatch(p->(p.getEventid()==eventid && email.equals(p.getEmail())));
	if(result)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			    .body(new MessageResponse("Feedback is already given"));
	}
			
	

	Feedback fdbobj=frepo.save(fdb);
	URI loc=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fdbobj.getFid()).toUri();
	SimpleMailMessage mailMessage= new SimpleMailMessage();
	mailMessage.setFrom(fromemail);
	mailMessage.setTo(email);
	mailMessage.setSubject("FEEDBACK");
	mailMessage.setText("The Feedback is successfully Submitted.");
	javaMailSender.send(mailMessage);
	return ResponseEntity.created(loc).body(new MessageResponse("Feedback is added"));
	
	

	}
	
	
	
	@GetMapping("/addfeedback/{eventid}")//To get feedback
	public ResponseEntity<List<Feedback>> showbyfeedbackbyeventid(@PathVariable("eventid") long eventid)
	{
	return new ResponseEntity<>(frepo.findByEventid(eventid),HttpStatus.OK);
	}
	
	
	


}
