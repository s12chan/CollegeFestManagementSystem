package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.demo.UserNotFoundException;
import com.example.demo.service.UserDetailsServiceImplementation;
import com.example.demo.utils.JwtRequest;
import com.example.demo.utils.JwtResponse;
import com.example.demo.utils.JwtUtil;

@RestController
//@CrossOrigin(origins="*")
public class LoginController
{
	@Autowired
	JwtUtil jutil;

	@Autowired
	UserDetailsServiceImplementation uservice;

	@Autowired
	AuthenticationManager authmanager;
	

	
	@PostMapping("/login")
    public ResponseEntity<Object> createToken(@RequestBody JwtRequest request)
    {
		
	
        
        try {
            authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            }
           
            catch(BadRequest e)
            {
                e.printStackTrace();
                
                throw new UserNotFoundException("Bad Credentials");
            }
           
            UserDetails udservice = uservice.loadUserByUsername(request.getUsername());
            String token =  jutil.generateToken(udservice);
            
           
            return ResponseEntity.ok(new JwtResponse(token));
       
       
       
    }
}
