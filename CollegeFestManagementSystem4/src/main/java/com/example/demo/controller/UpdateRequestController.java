package com.example.demo.controller;


import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.UserNotFoundException;
import com.example.demo.entity.UpdateRequest;
import com.example.demo.repository.EventInformationRepository;
import com.example.demo.repository.UpdateRequestRepository;

@RestController
public class UpdateRequestController 
{
	@Autowired
	UpdateRequestRepository urepo;
	
	@Autowired
	EventInformationRepository erepo;
	
	
	
	
	
		
	
	@PutMapping("/admin/updaterequeststatus/{uid}")//To update the update request status by the admin
	   public ResponseEntity<Object> updateverficationbyadmin(@PathVariable long uid,@RequestBody UpdateRequest urequest)
	{
	       UpdateRequest updateevent = urepo.findById(uid)
	               .orElseThrow(() -> new UserNotFoundException("Event does not not exist with this id: " + uid));

	       
	       updateevent.setUpdatestatus(urequest.getUpdatestatus());
	       
	       urepo.save(updateevent);
	            return ResponseEntity.ok(updateevent);
	  }
	
	@GetMapping("/admin/showupdaterequeststatus") //To get all the updaterequests
	public List<UpdateRequest> showallupdaterequests()
	{
	return urepo.findAll();
	}
	
	
	
}
