package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventAlreadyExistsException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	public EventAlreadyExistsException(String message)
	{
	super(message);
	}
}
