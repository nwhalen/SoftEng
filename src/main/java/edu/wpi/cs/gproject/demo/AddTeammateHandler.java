package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.http.AddTeammateRequest;
import edu.wpi.cs.gproject.demo.http.AddTeammateResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;

public class AddTeammateHandler implements RequestHandler <AddTeammateRequest,AddTeammateResponse>{
	
	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean addTeammate(String teammate, String name) throws Exception { 
		if (logger != null) { logger.log("in addTeammate"); }
		MappingDAO dao = new MappingDAO(logger);
		
		// check if present
		Mapping exist = dao.getMapping(name+teammate);
		Mapping mapping = new Mapping(teammate,name);
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
			if (addTeammate(req.teammate, req.name)) {
				response = new AddTeammateResponse(req.mappingID);
			} else {
				response = new AddTeammateResponse(req.name + req.teammate, 422);
			}
		} catch (Exception e) {
			response = new AddTeammateResponse("Unable to add teammate: " + req.mappingID + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
	
}
