package edu.wpi.cs.gproject.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.services.lambda.runtime.*;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.db.TaskDAO;
import edu.wpi.cs.gproject.demo.http.AllProjectsResponse;
import edu.wpi.cs.gproject.demo.http.AllTasksRequest;
import edu.wpi.cs.gproject.demo.http.AllTasksResponse;
import edu.wpi.cs.gproject.demo.http.AllTeammatesRequest;
import edu.wpi.cs.gproject.demo.http.AllTeammatesResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;
import edu.wpi.cs.gproject.demo.model.Project;
import edu.wpi.cs.gproject.demo.model.Task;

/**
 * Eliminated need to work with JSON
 */
public class ListAllTasksHandler implements RequestHandler<AllTasksRequest,AllTasksResponse> {

	public LambdaLogger logger;
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Task> getTasks(String name) throws Exception {
		logger.log("in getTasks");
		TaskDAO dao = new TaskDAO(logger);
		
		return dao.getAllTasks(name);
	}
	
	@Override
	public AllTasksResponse handleRequest(AllTasksRequest input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all tasks");
		logger.log(input.toString());

		AllTasksResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Task> list = getTasks(input.name);
			response = new AllTasksResponse(list, 200);
		} catch (Exception e) {
			response = new AllTasksResponse(403, e.getMessage());
		}
		
		return response;
	}
}
