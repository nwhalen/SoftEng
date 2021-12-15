package edu.wpi.cs.gproject.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.services.lambda.runtime.*;

import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.http.OneProjectRequest;
import edu.wpi.cs.gproject.demo.http.OneProjectResponse;
import edu.wpi.cs.gproject.demo.model.Project;

/**
 * Eliminated need to work with JSON
 */
public class GetProjectHandler implements RequestHandler<OneProjectRequest,OneProjectResponse> {

	public LambdaLogger logger;
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	Project getOneProject(String name) throws Exception {
		logger.log("in getProject");
		ProjectsDAO dao = new ProjectsDAO(logger);
		
		return dao.getProject(name);
	}
	
	@Override
	public OneProjectResponse handleRequest(OneProjectRequest input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all constants");
		logger.log(input.toString());

		OneProjectResponse response;
		try {
			// get all user defined projects
			Project p = getOneProject(input.name);
			response = new OneProjectResponse(p, 200);
		} catch (Exception e) {
			response = new OneProjectResponse(403, e.getMessage());
		}
		
		return response;
	}
}
