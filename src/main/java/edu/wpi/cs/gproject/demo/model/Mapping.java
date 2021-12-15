package edu.wpi.cs.gproject.demo.model;

public class Mapping {
	public final String taskID;
	public final String teammate;
	public final String name;
	public final String mappingID;
	
	public Mapping (String taskID, String teammate, String name) {
		this.taskID = taskID;
		this.teammate = teammate;
		this.name = name;
		this.mappingID = taskID + teammate;
	}
	public Mapping (String taskID, String teammate, String name, String mappingID) {
		this.taskID = taskID;
		this.teammate = teammate;
		this.name = name;
		this.mappingID = mappingID;
	}
	
	public Mapping(String teammate, String name) {
		this.taskID = "NULL";
		this.teammate = teammate;
		this.name = name;
		this.mappingID = name + teammate;
		
	}
	public Mapping () {
		taskID = "NULL";
		teammate = "NULL";
		name = "NULL";
		mappingID = "NULL";
	}
	
	
	/**
	 * Equality of Constants determined by name alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Mapping) {
			Mapping other = (Mapping) o;
			return mappingID.equals(other.mappingID);
		}
		
		return false;  // not a Mapping
	}

	public String toString() {
		String sysString = "";
		return "[" + taskID + teammate + sysString + "]";
	}

}
