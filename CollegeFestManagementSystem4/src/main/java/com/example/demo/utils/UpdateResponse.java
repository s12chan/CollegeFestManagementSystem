package com.example.demo.utils;

import java.net.URI;

public class UpdateResponse 
{
URI updatestatus;
String message;
public URI getUpdatestatus() {
	return updatestatus;
}
public void setUpdatestatus(URI updatestatus) {
	this.updatestatus = updatestatus;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public UpdateResponse(URI updatestatus, String message) {
	super();
	this.updatestatus = updatestatus;
	this.message = message;
}
@Override
public String toString() {
	return "UpdateResponse [updatestatus=" + updatestatus + ", message=" + message + "]";
}

}
