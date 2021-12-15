package edu.wpi.cs.gproject.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.gproject.demo.model.Task;

public class AllTasksResponse {
	public final List<Task> list;
	public final int statusCode;
	public final String error;
	
	public AllTasksResponse (List<Task> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public AllTasksResponse (int code, String errorMessage) {
		this.list = new ArrayList<Task>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyTasks"; }
		return "AllTasks(" + list.size() + ")";
	}
}
