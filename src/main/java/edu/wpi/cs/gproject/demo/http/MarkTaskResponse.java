package edu.wpi.cs.gproject.demo.http;

public class MarkTaskResponse {
	
	public final String response;
	public final int httpCode;
	
	public MarkTaskResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public MarkTaskResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
