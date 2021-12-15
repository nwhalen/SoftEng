package edu.wpi.cs.gproject.demo.http;

public class CreateProjectRequest {
	public String name;
	public double value;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public double getValue() { return value; }
	public void setValue(double d) { this.value = d; }
	
	public CreateProjectRequest() {
	}
	
	public CreateProjectRequest (String n, double val) {
		this.name = n;
		this.value = val;
	}
	
	public String toString() {
		return "CreateProject(" + name + "," + value + ")";
	}
}
