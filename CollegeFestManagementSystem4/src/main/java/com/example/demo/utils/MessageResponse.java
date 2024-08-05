package com.example.demo.utils;

public class MessageResponse 
{
String message;

public MessageResponse() {
	super();
}

public MessageResponse(String message) {
	super();
	this.message = message;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

@Override
public String toString() {
	return "MessageResponse [message=" + message + "]";
}

}
