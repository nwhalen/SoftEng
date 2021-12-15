package edu.wpi.cs.gproject.demo.http;

public class RemoveTeammateRequest {
	//public String mappingID;
	public String teammate;
	public String name;
	
//	public void setMappingID(String mappingID) {this.mappingID = mappingID; }
//	public String getMappingID() {return mappingID; }
	
	public void setName(String name) {this.name = name; }
	public String getName() {return name; }
	
	public void setTeammate(String teammate) {this.teammate = teammate; }
	public String getTeammate() {return teammate; }
	
	public RemoveTeammateRequest (String t, String n) {
		this.teammate = t;
		this.name = n;
	}

	public RemoveTeammateRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + name + ", " + teammate + ")";
	}
}
