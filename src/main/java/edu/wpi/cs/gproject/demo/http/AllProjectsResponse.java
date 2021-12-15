package edu.wpi.cs.gproject.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.gproject.demo.model.Project;

public class AllProjectsResponse {
	public final List<Project> list;
	public final int statusCode;
	public final String error;
	
	public AllProjectsResponse (List<Project> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public AllProjectsResponse (int code, String errorMessage) {
		this.list = new ArrayList<Project>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyConstants"; }
		return "AllConstants(" + list.size() + ")";
	}
}
