package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.http.AddTeammateRequest;
import edu.wpi.cs.gproject.demo.http.AddTeammateResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;

public class AddTeammateHandlerOLD implements RequestHandler <AddTeammateRequest,AddTeammateResponse>{
	
	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean addTeammate(String taskID, String teammate, String name, String mappingID) throws Exception { 
		if (logger != null) { logger.log("in addTeammate"); }
		MappingDAO dao = new MappingDAO(logger);
		
		// check if present
		Mapping exist = dao.getMapping(mappingID);
		Mapping mapping = new Mapping(taskID,teammate,name,mappingID);
		if (exist == null) {
			return dao.addMapping(mapping);
		} else {
			return false;
		}
	}
	
	@Override 
	public AddTeammateResponse handleRequest(AddTeammateRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		AddTeammateResponse response;
		try {
			if (addTeammate(req.taskID, req.teammate, req.name, req.mappingID)) {
				response = new AddTeammateResponse(req.mappingID);
			} else {
				response = new AddTeammateResponse(req.mappingID, 422);
			}
		} catch (Exception e) {
			response = new AddTeammateResponse("Unable to add teammate: " + req.taskID + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
	
}
