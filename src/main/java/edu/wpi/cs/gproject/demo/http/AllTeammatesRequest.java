package edu.wpi.cs.gproject.demo.http;

public class AllTeammatesRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public AllTeammatesRequest() {
	}
	
	public AllTeammatesRequest (String n) {
		this.name = n;
	}
	
	public String toString() {
		return "GetProject(" + name + ")";
	}
}
