package edu.wpi.cs.gproject.demo.http;

public class AddTeammateRequest {
	public String taskID;
	public String teammate;
	public String name;
	public String mappingID;
	
	public String getTaskID( ) { return taskID; }
	public void setTaskID(String taskID) { this.taskID = taskID; }
	
	public String getTeammate( ) { return teammate; }
	public void setTeammate(String teammate) { this.teammate = teammate; }
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getMappingID( ) { return mappingID; }
	public void setMappingID(String mappingID) { this.mappingID = mappingID; }
	
	public AddTeammateRequest() {
	}
	
	public AddTeammateRequest (String taskID, String teammate, String name, String mappingID) {
		this.taskID = taskID;
		this.teammate = teammate;
		this.name = name;
		this.mappingID = mappingID;
	}
	
	public String toString() {
		return "AddTeammate(" + taskID + "," + teammate + "," + name + "," + mappingID + ")";
	}

}
