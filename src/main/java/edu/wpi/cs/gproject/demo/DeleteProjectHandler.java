package edu.wpi.cs.gproject.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.http.DeleteProjectRequest;
import edu.wpi.cs.gproject.demo.http.DeleteProjectResponse;
import edu.wpi.cs.gproject.demo.model.Project;

/**
 * No more JSON parsing
 */
public class DeleteProjectHandler implements RequestHandler<DeleteProjectRequest,DeleteProjectResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteProjectResponse handleRequest(DeleteProjectRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteProjectResponse response = null;
		logger.log(req.toString());

		ProjectsDAO dao = new ProjectsDAO(logger);

		// MAKE sure that we prevent attempts to delete system constants...
		
		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		Project project = new Project(req.name, 0);
		try {
			if (dao.deleteProject(project) && dao.deleteTasks(project) && dao.deleteTeammates(project)) {
				response = new DeleteProjectResponse(req.name, 200);
			} else {
				response = new DeleteProjectResponse(req.name, 422, "Unable to delete project.");
			}
		} catch (Exception e) {
			response = new DeleteProjectResponse(req.name, 403, "Unable to delete project: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
