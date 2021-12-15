package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.db.TaskDAO;
import edu.wpi.cs.gproject.demo.http.AssignTeammateToTaskRequest;
import edu.wpi.cs.gproject.demo.http.AssignTeammateToTaskResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;
import edu.wpi.cs.gproject.demo.model.Task;

public class AssignTeammateToTaskHandler implements RequestHandler <AssignTeammateToTaskRequest,AssignTeammateToTaskResponse>{
	
	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean assignTeammateToTask(String teammate, String taskName) throws Exception { 
		if (logger != null) { logger.log("beginning assign tm8 to task"); }
		MappingDAO dao = new MappingDAO(logger);
		TaskDAO tdao = new TaskDAO(logger);
		
		// check if present
		Task t = tdao.getTask(taskName);
		logger.log("task : " + t);
		Mapping exist = dao.getMapping(t.name+teammate);
		Mapping mapping = new Mapping(t.taskPK,teammate,t.name, t.taskID + t.name);
		logger.log("adding " + teammate + "to mapping ID" + t.taskID + t.name);
		if (exist == null) {
			logger.log("Teammate assigned to task, creating mapping: " + mapping);
			return dao.addMapping(mapping);
		} else {
			return false;
		}
	}
	
	
	@Override 
	public AssignTeammateToTaskResponse handleRequest(AssignTeammateToTaskRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		AssignTeammateToTaskResponse response;
		try {
			//req.taskID is serving as taskName (yikes) ._.
			if (assignTeammateToTask(req.teammate, req.taskID)) {
				response = new AssignTeammateToTaskResponse(req.mappingID);
			} else {
				response = new AssignTeammateToTaskResponse(req.name + req.teammate, 422);
			}
		} catch (Exception e) {
			response = new AssignTeammateToTaskResponse("Unable to add teammate to task: " + req.mappingID + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
	
}
