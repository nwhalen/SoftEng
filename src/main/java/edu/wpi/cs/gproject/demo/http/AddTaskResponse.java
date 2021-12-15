package edu.wpi.cs.gproject.demo.http;

public class AddTaskResponse {
	
	public final String response;
	public final int httpCode;
	
	public AddTaskResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public AddTaskResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
