package com.example.demo;

public class UserAlreadyExistsException extends RuntimeException 
{
	public UserAlreadyExistsException(String message)
	{
		super(message);
	}
}
