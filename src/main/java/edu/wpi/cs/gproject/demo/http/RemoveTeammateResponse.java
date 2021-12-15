package edu.wpi.cs.gproject.demo.http;

/** Sends back the name of the constant deleted -- easier to handle on client-side. */
public class RemoveTeammateResponse {
	public final String mappingID;
	public final int statusCode;
	public final String error;
	
	public RemoveTeammateResponse (String mappingID, int statusCode) {
		this.mappingID = mappingID;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public RemoveTeammateResponse (String mappingID, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.mappingID = mappingID;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "DeleteResponse(" + mappingID + ")";
		} else {
			return "ErrorResult(" + mappingID + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
