package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeedbackAlreadyGivenException extends RuntimeException 
{


public FeedbackAlreadyGivenException(String message)
{
	super(message);
}
}
