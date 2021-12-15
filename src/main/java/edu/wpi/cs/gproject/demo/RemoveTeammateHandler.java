package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.http.RemoveTeammateRequest;
import edu.wpi.cs.gproject.demo.http.RemoveTeammateResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;
import edu.wpi.cs.gproject.demo.model.Project;

/**
 * No more JSON parsing
 */
public class RemoveTeammateHandler implements RequestHandler<RemoveTeammateRequest,RemoveTeammateResponse> {

	public LambdaLogger logger = null;

	@Override
	public RemoveTeammateResponse handleRequest(RemoveTeammateRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to remove");

		RemoveTeammateResponse response = null;
		logger.log(req.toString());

		MappingDAO dao = new MappingDAO(logger);

		// MAKE sure that we prevent attempts to delete system constants...
		
		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		Mapping mapping = new Mapping(req.teammate, req.name);
		try {
			if (dao.deleteMapping(mapping)) {
				response = new RemoveTeammateResponse(req.name, 200);
			} else {
				response = new RemoveTeammateResponse(req.name, 422, "Unable to remove teammate.");
			}
		} catch (Exception e) {
			response = new RemoveTeammateResponse(req.name, 403, "Unable to remove teammate: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
