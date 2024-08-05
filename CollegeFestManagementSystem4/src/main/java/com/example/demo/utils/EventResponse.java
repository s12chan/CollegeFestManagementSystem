package com.example.demo.utils;

import java.net.URI;

public class EventResponse 
{
String message;
URI viewevents;
URI raiseupdaterequest;
URI viewupdaterequest;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public URI getViewevents() {
	return viewevents;
}
public void setViewevents(URI viewevents) {
	this.viewevents = viewevents;
}
public URI getRaiseupdaterequest() {
	return raiseupdaterequest;
}
public void setRaiseupdaterequest(URI raiseupdaterequest) {
	this.raiseupdaterequest = raiseupdaterequest;
}

public URI getViewupdaterequest() {
	return viewupdaterequest;
}
public void setViewupdaterequest(URI viewupdaterequest) {
	this.viewupdaterequest = viewupdaterequest;
}
public EventResponse(String message, URI viewevents, URI raiseupdaterequest, URI viewupdaterequest) {
	super();
	this.message = message;
	this.viewevents = viewevents;
	this.raiseupdaterequest = raiseupdaterequest;
	this.viewupdaterequest = viewupdaterequest;
}
@Override
public String toString() {
	return "EventResponse [message=" + message + ", viewevents=" + viewevents + ", raiseupdaterequest="
			+ raiseupdaterequest + ", viewupdaterequest=" + viewupdaterequest + "]";
}






}
