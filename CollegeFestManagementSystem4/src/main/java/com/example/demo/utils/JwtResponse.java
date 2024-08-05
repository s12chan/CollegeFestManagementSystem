package com.example.demo.utils;

public class JwtResponse 
{
String jsontokenstring;

public JwtResponse() {
	super();
}

public JwtResponse(String jsontokenstring) {
	super();
	this.jsontokenstring = jsontokenstring;
}

public String getJsontokenstring() {
	return jsontokenstring;
}

public void setJsontokenstring(String jsontokenstring) {
	this.jsontokenstring = jsontokenstring;
}

@Override
public String toString() {
	return "JwtResponse [jsontokenstring=" + jsontokenstring + "]";
}

}
