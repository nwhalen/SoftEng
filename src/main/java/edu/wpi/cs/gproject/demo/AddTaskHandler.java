package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.MappingDAO;
import edu.wpi.cs.gproject.demo.db.TaskDAO;
import edu.wpi.cs.gproject.demo.http.AddTaskRequest;
import edu.wpi.cs.gproject.demo.http.AddTaskResponse;
import edu.wpi.cs.gproject.demo.http.AddTeammateRequest;
import edu.wpi.cs.gproject.demo.http.AddTeammateResponse;
import edu.wpi.cs.gproject.demo.model.Mapping;
import edu.wpi.cs.gproject.demo.model.Task;

public class AddTaskHandler implements RequestHandler <AddTaskRequest,AddTaskResponse>{
	
	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	/*
	boolean addTask(String taskID, String taskName, int completed, String name) throws Exception { 
		if (logger != null) { logger.log("in addTask"); }
		TaskDAO dao = new TaskDAO(logger);
		
		// check if present
		Task exist = dao.getTask(taskID);
		//int tid = Integer.parseInt(exist.getTaskID());
		int taskmax = dao.calculateTaskID(name);
		
		String taskIDstring = Integer.toString(taskmax);
		Task task = new Task(taskIDstring, taskName, completed, name);
		if (exist == null) {
			return dao.addTask(task);
		} else {
			return false;
		}
	}
	*/
	boolean addTask(String taskName, String name) throws Exception { 
		if (logger != null) { logger.log("in addTask"); }
		TaskDAO dao = new TaskDAO(logger);
		
		// check if present
		Task exist = dao.getTask(taskName);
		//int tid = Integer.parseInt(exist.getTaskID());
		logger.log("about to calculate taskID");
		int taskmax = dao.calculateTaskID(name);
		logger.log("calculated taskID: " + taskmax);
		
		
		String taskIDstring = Integer.toString(taskmax);
		logger.log("taskID String = " + taskIDstring);
		String taskPK = taskIDstring + name;
		logger.log("taskPK: = " + taskPK);
		Task task = new Task(taskIDstring, taskName, 0, name, taskPK);
		if (exist == null) {
			logger.log("adding task: " + task);
			return dao.addTask(task);
		} else {
			return false;
		}
	}
	
	@Override 
	public AddTaskResponse handleRequest(AddTaskRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		AddTaskResponse response;
		try {
			if (addTask(req.taskName, req.name)) {
				response = new AddTaskResponse(req.taskID);
			} else {
				response = new AddTaskResponse(req.taskID + req.taskName + req.completed + req.name + req.taskPK + 422);
			}
		} catch (Exception e) {
			response = new AddTaskResponse("Unable to add task: " + req.taskID + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
	
}
