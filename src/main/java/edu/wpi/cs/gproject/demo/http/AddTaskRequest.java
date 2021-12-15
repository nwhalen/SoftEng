package edu.wpi.cs.gproject.demo.http;

public class AddTaskRequest {
	public String taskID;
	public String taskName;
	public int completed;
	public String name;
	public String taskPK;
	
	public String getTaskID( ) { return taskID; }
	public void setTaskID(String taskID) { this.taskID = taskID; }
	
	public String getTaskName( ) { return taskName; }
	public void setTaskName(String taskName) { this.taskName = taskName; }
	
	public int getCompleted() {return completed;}
	public void setCompleted(int c) {this.completed = c;}
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getTaskPK() { return taskPK;}
	public void setTaskPK(String taskPK) { this.taskPK = taskPK;}
	
	public AddTaskRequest() {
	}
	
	public AddTaskRequest (String taskID, String taskName, int completed, String name, String taskPK) {
		this.taskID = taskID;
		this.taskName = taskName;
		this.completed = completed;
		this.name = name;
		this.taskPK = taskPK;
	}
	
	public String toString() {
		return "AddTask(" + taskID + "," + taskName + "," + completed + "," + name + taskPK + ")";
	}

}
