package edu.wpi.cs.gproject.demo.model;

public class Task {
	public final String taskID;
	public final String taskName; //task name
	public final int completed;
	public final String name; //project name
	public final String taskPK;

	public Task(String taskID, String taskName, int completed, String name) {
		this.taskID = taskID;
		this.taskName = taskName;
		this.completed = completed;
		this.name = name;
		this.taskPK = taskID + name;
	}
	
	public Task(String taskID, String taskName, int completed, String name, String taskPK) {
		this.taskID = taskID;
		this.taskName = taskName;
		this.completed = completed;
		this.name = name;
		this.taskPK = taskPK;
	}
		
	public Task(String taskID, String taskName, String name) {
		this.taskID = taskID;
		this.taskName = taskName;
		this.completed = 0;
		this.name = name;
		this.taskPK = taskID + name;
	}
	
	public Task (String taskName) {
		this.taskName = taskName;
		this.taskID = "0";
		this.name = "NONAME";
		this.completed = 0;
		this.taskPK = taskID + name;
	}
	
	public String getTaskID() {
		return this.taskID;
	}
	
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Task) {
			Mapping other = (Mapping) o;
			return taskID.equals(other.taskID);
		}
		
		return false;  // not a Task
	}

	public String toString() {
		String sysString = "";
		return "[" + taskID + taskName + name + sysString + "]";
	}

}

