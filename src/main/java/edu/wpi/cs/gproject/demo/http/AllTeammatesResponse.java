package edu.wpi.cs.gproject.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.gproject.demo.model.Mapping;
import edu.wpi.cs.gproject.demo.model.Project;

public class AllTeammatesResponse {
	public final List<Mapping> list;
	public final int statusCode;
	public final String error;
	
	public AllTeammatesResponse (List<Mapping> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public AllTeammatesResponse (int code, String errorMessage) {
		this.list = new ArrayList<Mapping>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyTeammates"; }
		return "AllTeammates(" + list.size() + ")";
	}
}
