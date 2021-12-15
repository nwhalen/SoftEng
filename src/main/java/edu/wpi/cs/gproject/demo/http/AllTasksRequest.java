package edu.wpi.cs.gproject.demo.http;

public class AllTasksRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public AllTasksRequest() {
	}
	
	public AllTasksRequest (String n) {
		this.name = n;
	}
	
	public String toString() {
		return "GetTask(" + name + ")";
	}
}
