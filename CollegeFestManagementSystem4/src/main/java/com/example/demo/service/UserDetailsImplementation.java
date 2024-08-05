package com.example.demo.service;


import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.User;


public class UserDetailsImplementation implements UserDetails
{

private static final long serialVersionUID = 1L;
private transient User user;


public UserDetailsImplementation() {
super();
}


public UserDetailsImplementation(User user) {
super();
this.user = user;
}


@Override
public Collection<? extends GrantedAuthority> getAuthorities()
{

@SuppressWarnings("rawtypes")
HashSet<SimpleGrantedAuthority> set=new HashSet();
set.add(new SimpleGrantedAuthority(user.getRole()));

return set;
}

@Override
public String getPassword() {

return user.getPassword();
}

@Override
public String getUsername() {

return user.getUsername();
}

@Override
public boolean isAccountNonExpired() {

return true;
}

@Override
public boolean isAccountNonLocked() {

return true;
}

@Override
public boolean isCredentialsNonExpired() {

return true;
}

@Override
public boolean isEnabled() {

return true;
}

}
