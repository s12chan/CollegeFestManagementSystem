package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.UserAlreadyExistsException;
import com.example.demo.UserNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtRequest;

@RestController
@CrossOrigin(origins="*")
public class SignupController 
{
	@Autowired
	UserRepository urepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Value("${spring.mail.username}")
	private String fromemail;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@GetMapping("/showallusersandadmins") 
	public List<User> showall()
	{
	return urepo.findAll();
	}
	
	
	@PostMapping("/signup")//new user is signing up
	public ResponseEntity<EntityModel<Object>> add(@Valid @RequestBody User user)
	{
		 
			if (Boolean.TRUE.equals(urepo.existsByUsername(user.getUsername()))) 
			{
			      throw new UserAlreadyExistsException("This username already exists");
			 }
			
	user.setPassword(encoder.encode(user.getPassword()));
	User empobj=urepo.save(user);
	JwtRequest jobj=new JwtRequest();
	EntityModel<Object> entitymodel=EntityModel.of(empobj);
	Link link;
	link = WebMvcLinkBuilder.linkTo(methodOn(LoginController.class).createToken(jobj)).withRel("Login");
	entitymodel.add(link);
	SimpleMailMessage mailMessage= new SimpleMailMessage();
	mailMessage.setFrom(fromemail);
	mailMessage.setTo(user.getUsername());
	mailMessage.setText("You are Successfully signedup.");
	mailMessage.setSubject("SIGNUP CONFIRMATION");
	javaMailSender.send(mailMessage);
	return ResponseEntity.ok(entitymodel);

	}
	
	@PutMapping("/updateprofile/{username}")//To update registered user's profile
	   public ResponseEntity<Object> updateEmployee(@PathVariable String username,@RequestBody User user)
	{
	       User updateprofile = urepo.findById(username)
	               .orElseThrow(() -> new UserNotFoundException("User does not not exist: " + username));

	       updateprofile.setName(user.getName());
	       updateprofile.setPassword(user.getPassword());
	       updateprofile.setMobilenumber(user.getMobilenumber());
	       
	       
	       
	       
	       urepo.save(updateprofile);
	            return ResponseEntity.ok(updateprofile);
	   }
}
