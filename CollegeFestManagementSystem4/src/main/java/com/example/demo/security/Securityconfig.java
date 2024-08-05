package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.UserDetailsServiceImplementation;
import com.example.demo.utils.JwtFilter;

@Configuration
@EnableWebSecurity
public class Securityconfig 
{
	@Autowired
	UserDetailsServiceImplementation userds;

	
	@Bean
	  public JwtFilter jwtfilter() {
	    return new JwtFilter();
	  }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
      http.cors().and().csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
          .authorizeRequests().antMatchers("/login").permitAll()
          .antMatchers("/signup").permitAll()
          //.antMatchers("/showallevents,/addfeedback").permitAll()
          .antMatchers("/showallevents").permitAll()
          .antMatchers("/addfeedback").permitAll()
          .antMatchers("/swagger-ui/**","/v2/**","/swagger-resources/**").permitAll()
          .antMatchers("/viewevent/{festtype}").permitAll()
          .antMatchers("/vieweventbetweendate/{startdate}/{enddate}").permitAll()
          .antMatchers("/vieweventbylocation/{address}").permitAll()
          .antMatchers("/updateprofile/{username}").permitAll()
          .antMatchers("/showallfeedback,/showallusersandadmins,/admin/updateeventstatus/{eventid},/admin/showallevents,/admin/showallunverifiedevents,/admin/deleteevent/{eventid}").hasRole("ADMIN")
          .antMatchers("/admin/updaterequeststatus/{uid},/admin/showupdaterequeststatus,/event/updateevent/{eventid},/admin/deleteuser/{username}").hasRole("ADMIN")
          .antMatchers("/event,/event/viewuserevent,/event/request,/user/showupdaterequest/{uid},/event/request/viewupdaterequest,/addfeedback/{id},/addfeedback/{eventid},/viewefeedbackbyeventid/{eventid}").hasRole("NORMAL")
          .anyRequest().authenticated();
      
    

      http.addFilterBefore(jwtfilter(), UsernamePasswordAuthenticationFilter.class);
      
      return http.build();
    }
}
