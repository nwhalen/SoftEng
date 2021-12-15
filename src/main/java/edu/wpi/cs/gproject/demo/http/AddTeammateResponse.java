package edu.wpi.cs.gproject.demo.http;

public class AddTeammateResponse {
	
	public final String response;
	public final int httpCode;
	
	public AddTeammateResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public AddTeammateResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
