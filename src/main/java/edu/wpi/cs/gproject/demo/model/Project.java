package edu.wpi.cs.gproject.demo.model;

import edu.wpi.cs.gproject.demo.model.Project;

public class Project {
	public final String name;
	public final double value;
	public final int archived;
	
	public Project (String name, double value) {
		this.name = name;
		this.value = value;
		this.archived = 0;
	}
	
	public Project () {
		name = "NULL";
		value = 0;
		this.archived = 0;
	}
	
	/**
	 * Equality of Constants determined by name alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Project) {
			Project other = (Project) o;
			return name.equals(other.name);
		}
		
		return false;  // not a Constant
	}

	public String toString() {
		String sysString = "";
		return "[" + name+ " = " + value + sysString + "]";
	}
}
