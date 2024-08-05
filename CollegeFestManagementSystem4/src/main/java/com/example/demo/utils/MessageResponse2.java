package com.example.demo.utils;

import java.net.URI;

public class MessageResponse2 
{
String message;
URI viewupdaterequest;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public URI getViewupdaterequest() {
	return viewupdaterequest;
}
public void setViewupdaterequest(URI viewupdaterequest) {
	this.viewupdaterequest = viewupdaterequest;
}
public MessageResponse2(String message, URI viewupdaterequest) {
	super();
	this.message = message;
	this.viewupdaterequest = viewupdaterequest;
}
@Override
public String toString() {
	return "MessageResponse2 [message=" + message + ", viewupdaterequest=" + viewupdaterequest + "]";
}

}
