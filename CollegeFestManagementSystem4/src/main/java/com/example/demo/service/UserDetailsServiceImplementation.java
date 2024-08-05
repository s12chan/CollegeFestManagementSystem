package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.UserNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService
{
@Autowired
UserRepository userrepo;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
{

	 User user = userrepo.findById(username)
		        .orElseThrow(() -> new UserNotFoundException("User Not Found with username: " + username));
return new UserDetailsImplementation(user);
}

}
