package edu.wpi.cs.gproject.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.services.lambda.runtime.*;

import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.http.AllProjectsResponse;
import edu.wpi.cs.gproject.demo.model.Project;

/**
 * Eliminated need to work with JSON
 */
public class ListAllProjectsHandler implements RequestHandler<Object,AllProjectsResponse> {

	public LambdaLogger logger;
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Project> getProjects() throws Exception {
		logger.log("in getProjects");
		ProjectsDAO dao = new ProjectsDAO(logger);
		
		return dao.getAllProjects();
	}
	
	@Override
	public AllProjectsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all constants");

		AllProjectsResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Project> list = getProjects();
			response = new AllProjectsResponse(list, 200);
		} catch (Exception e) {
			response = new AllProjectsResponse(403, e.getMessage());
		}
		
		return response;
	}
}
