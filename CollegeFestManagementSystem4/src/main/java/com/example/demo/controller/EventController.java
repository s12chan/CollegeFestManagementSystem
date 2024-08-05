package com.example.demo.controller;

import java.net.URI;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.EventAlreadyExistsException;
import com.example.demo.EventNotFoundException;
import com.example.demo.UserNotFoundException;
import com.example.demo.entity.EventInformation;
import com.example.demo.entity.UpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.EventInformationRepository;
import com.example.demo.repository.UpdateRequestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.MessageResponse;
import com.example.demo.utils.MessageResponse2;


@RestController
@CrossOrigin(origins="*")
public class EventController
{
	@Value("${status1}")
	private String intialstatus;
	
	@Value("${spring.mail.username}")
	private String fromemail;

	@Autowired
	EventInformationRepository erepo;
	
	@Autowired
	UpdateRequestRepository urepo;
	
	@Autowired
	UserRepository usrepo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	String status="verified";

	
	@PostMapping("/event")//To add an event by the registered user
	public ResponseEntity<Object> add(@Valid @RequestBody EventInformation event,Authentication authentication)
	{
		String username=authentication.getName();
		
		
		User user=usrepo.findById(username)
				.orElseThrow(() -> new UserNotFoundException("User does not not exist with this username: " + username));
		if (Boolean.TRUE.equals(erepo.existsByFesttypeAndEventname(event.getFesttype(),event.getEventname()))) 
		{
		      throw new EventAlreadyExistsException("This event already exists");
		 }
		
		Date sdate=event.getStartdate();
		Date edate=event.getEnddate();
		Date rdate=event.getLastdateforregistration();
		Date date = new Date();
		if(sdate.before(date))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				    .body(new MessageResponse("Enter the correct start date"));
		}
		if(edate.before(date) || edate.before(sdate))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				    .body(new MessageResponse("Enter the correct end date"));
		}
		if(rdate.after(sdate) || rdate.after(edate) || rdate.before(date))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				    .body(new MessageResponse("Enter the correct last date for registration"));
		}
		event.setUser(user);
		event.setStatus(intialstatus);
	EventInformation eventobj=erepo.save(event);
	URI loc=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventobj.getEventid()).toUri();
	SimpleMailMessage mailMessage= new SimpleMailMessage();
	mailMessage.setFrom(fromemail);
	mailMessage.setTo(username);
	mailMessage.setText("The event is successfully added."+"\n"+"The event id is "+event.getEventid()+"\n\n"+"Note:The event details will be displayed only after verification.");
	mailMessage.setSubject("Event Added");
	javaMailSender.send(mailMessage);
	return ResponseEntity.created(loc).body(new MessageResponse("The new event is successfully added"));
	

	}
	
	@GetMapping("/event/viewuserevent")//To get registered user's all the events
	public ResponseEntity<List<EventInformation>> showbyemail(Authentication authentication)
	{
		String username=authentication.getName();
	return new ResponseEntity<>(erepo.findByEmail(username),HttpStatus.OK);
	}
	
	
	@GetMapping("/vieweventbetweendate/{startdate}/{enddate}")//To get the events within the range
	public ResponseEntity<List<EventInformation>> showbydate(@PathVariable("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,@PathVariable("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate)
	{
		Date date=new Date();
		
	return new ResponseEntity<>(erepo.findByStartdateBetweenAndStatusAndStartdateAfter(startdate,enddate,status,date),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/viewevent/{festtype}")//To get either technical or cultural fests
	public ResponseEntity<List<EventInformation>> showbyfesttype(@PathVariable("festtype") String festtype)
	{
		Date date=new Date();
	return new ResponseEntity<>(erepo.findByFesttypeAndStatusAndStartdateAfter(festtype,status,date),HttpStatus.OK);
	}
	
	@GetMapping("/vieweventbylocation/{address}")//To get events based on location
	public ResponseEntity<List<EventInformation>> showbyfestbylocation(@PathVariable("address") String address)
	{
		Date date=new Date();
	return new ResponseEntity<>(erepo.findByAddressStartingWithAndStatusAndStartdateAfter(address,status,date),HttpStatus.OK);
	}
	

	@PutMapping("/event/updateevent/{eventid}")//To update registered user's event
	   public ResponseEntity<Object> updateEmployee(@PathVariable long eventid,@RequestBody EventInformation event)
	{
	       EventInformation updateevent = erepo.findById(eventid)
	               .orElseThrow(() -> new UserNotFoundException("Event does not not exist with this id: " + eventid));

	      updateevent.setEmail(event.getEmail());
	       updateevent.setPoc(event.getPoc());
	       updateevent.setFesttype(event.getFesttype());
	       updateevent.setEventname(event.getEventname());
	       updateevent.setAddress(event.getAddress());
	       updateevent.setStartdate(event.getStartdate());
	       updateevent.setEnddate(event.getEnddate());
	       updateevent.setLastdateforregistration(event.getLastdateforregistration());
	       updateevent.setDescription(event.getDescription());
	       updateevent.setRegistrationfees(event.getRegistrationfees());
	       updateevent.setRegistrationlink(event.getRegistrationlink());
	       
	       
	       erepo.save(updateevent);
	            return ResponseEntity.ok(updateevent);
	      
	       
	       
	   }
	
	@PostMapping("/event/request")//user raises an updaterequest
	public ResponseEntity<Object> addupdaterequest(@Valid @RequestBody UpdateRequest urequest,Authentication authentication)
	{
		String username=authentication.getName();
	long eventid=urequest.getEventid();
	String email=urequest.getUsername();
	String description=urequest.getUdiscription();
	EventInformation eobj=erepo.findById(eventid)
			.orElseThrow(() -> new EventNotFoundException("Event does not not exist with this ID: " + eventid));

	urequest.setEinformation(eobj);
	List<UpdateRequest> updatelist=urepo.findAll();
	boolean result=updatelist.stream().anyMatch(p->(p.getEventid()==eventid && description.equals(p.getUdiscription())));
	if(result)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			    .body(new MessageResponse("Update request is already present"));
	}
	List<EventInformation> eventslist=erepo.findAll();
	for(EventInformation e:eventslist)
	{
		
		if(eventid==e.getEventid() && e.getEmail().equals(email))
		{
			
			urequest.setUpdatestatus("Not Updated");
			UpdateRequest uobj=urepo.save(urequest);
			URI loc=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uobj.getUid()).toUri();
			URI loc1=ServletUriComponentsBuilder.fromCurrentRequest().path("/viewupdaterequest").buildAndExpand().toUri();
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom(fromemail);
			mailMessage.setTo(username);
			mailMessage.setText("The Update Request is successfully raised."+"\n"+"The update request id is "+urequest.getUid()+"\n\n"+"Note:We will notify you once the details are updated.");
			mailMessage.setSubject("Update Request Raised");
			javaMailSender.send(mailMessage);
			return ResponseEntity.created(loc).body(new MessageResponse2("The update request is successfully raised",loc1));
			
		}
	
	}
			
	

	return ResponseEntity.ok("Please...enter the correct credentials"); 

	}
	
	
	@GetMapping("/event/request/viewupdaterequest")//To get registered user's all the events
	public ResponseEntity<List<UpdateRequest>> showupdaterequest(Authentication authentication)
	{
		String username=authentication.getName();
	return new ResponseEntity<>(urepo.findByUsername(username),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/showallevents")//To get all the verified events
	public ResponseEntity<List<EventInformation>> showAllVerifiedEvents()
	{
	Date startdate=new Date();	
	return new ResponseEntity<>(erepo.findByStatusAndStartdateAfter(status,startdate),HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/deleteuser/{username}")//To delete user's account
	public ResponseEntity<Object> deleteuser(@PathVariable String username)
	{
	Optional<User> uobj=usrepo.findById(username);
	if(!uobj.isPresent())
	{
		return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("The event ID does no exist"));
	}
	usrepo.deleteById(username);
	return ResponseEntity.ok("The user is deleted");
	}
	
	
	
	
}
