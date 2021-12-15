package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.db.TaskDAO;
import edu.wpi.cs.gproject.demo.http.MarkTaskRequest;
import edu.wpi.cs.gproject.demo.http.MarkTaskResponse;
import edu.wpi.cs.gproject.demo.model.Task;

public class MarkTaskHandler implements RequestHandler <MarkTaskRequest,MarkTaskResponse>{
	
	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	
//removed project name from signature
	boolean markTask(String taskName) throws Exception { 
		if (logger != null) { logger.log("in markTask"); }
		TaskDAO dao = new TaskDAO(logger);
		ProjectsDAO pdao = new ProjectsDAO(logger);
		
		// check if present
		Task task = dao.getTask(taskName);
		logger.log("about to mark Task " + task.taskName);
		
		
		
		if (task != null) {
			logger.log("updating task: " + task);
			double totalTasks = dao.totalTasks(task.name);
			double completedTasks = dao.completedTasks(task.name);
			double completionValue = completedTasks / totalTasks;
			logger.log("total tasks: " + totalTasks + " completed tasks: " + completedTasks + "project completion: " + completionValue);
			pdao.updateProjectCompletion(task.name, completionValue);
			return dao.updateTask(task);
			
			
		} else {
			return false;
		}
	}
	
	@Override 
	public MarkTaskResponse handleRequest(MarkTaskRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		MarkTaskResponse response;
		try {
			if (markTask(req.taskName)) {
				response = new MarkTaskResponse(req.taskID);
			} else {
				response = new MarkTaskResponse(req.taskID + req.taskName + req.completed + req.name + req.taskPK + 422);
			}
		} catch (Exception e) {
			response = new MarkTaskResponse("Unable to mark task: " + req.taskID + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
	
}
