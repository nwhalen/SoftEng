package edu.wpi.cs.gproject.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.gproject.demo.model.Project;

public class OneProjectResponse {
	public final Project p;
	public final int statusCode;
	public final String error;
	
	public OneProjectResponse (Project p, int code) {
		this.p = p;
		this.statusCode = code;
		this.error = "";
	}
	
	public OneProjectResponse (int code, String errorMessage) {
		this.p = new Project();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (p == null) { return "NoProject"; }
		return p.toString();
	}
}
