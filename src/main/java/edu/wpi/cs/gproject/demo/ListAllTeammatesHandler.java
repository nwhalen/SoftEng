package edu.wpi.cs.gproject.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.services.lambda.runtime.*;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.http.AllProjectsResponse;
import edu.wpi.cs.gproject.demo.http.AllTeammatesRequest;
import edu.wpi.cs.gproject.demo.http.AllTeammatesResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;
import edu.wpi.cs.gproject.demo.model.Project;

/**
 * Eliminated need to work with JSON
 */
public class ListAllTeammatesHandler implements RequestHandler<AllTeammatesRequest,AllTeammatesResponse> {

	public LambdaLogger logger;
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Mapping> getTeammates(String name) throws Exception {
		logger.log("in getTeammates");
		MappingDAO dao = new MappingDAO(logger);
		
		return dao.getAllMappings(name);
	}
	
	@Override
	public AllTeammatesResponse handleRequest(AllTeammatesRequest input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all teammates");
		logger.log(input.toString());

		AllTeammatesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Mapping> list = getTeammates(input.name);
			response = new AllTeammatesResponse(list, 200);
		} catch (Exception e) {
			response = new AllTeammatesResponse(403, e.getMessage());
		}
		
		return response;
	}
}
