package edu.wpi.cs.gproject.demo;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import edu.wpi.cs.gproject.demo.db.ProjectsDAO;
import edu.wpi.cs.gproject.demo.http.CreateProjectRequest;
import edu.wpi.cs.gproject.demo.http.CreateProjectResponse;
import edu.wpi.cs.gproject.demo.model.Project;

/**
 * Create a new constant and store in S3 bucket.

 * @author heineman
 */
public class CreateProjectHandler implements RequestHandler<CreateProjectRequest,CreateProjectResponse> {

	LambdaLogger logger;
	
	// To access S3 storage
	private AmazonS3 s3 = null;
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "projects/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createProject(String name, double value) throws Exception { 
		if (logger != null) { logger.log("in createProject"); }
		ProjectsDAO dao = new ProjectsDAO(logger);
		
		// check if present
		Project exist = dao.getProject(name);
		Project project = new Project (name, value);
		if (exist == null) {
			return dao.addProject(project);
		} else {
			return false;
		}
	}
	
	@Override 
	public CreateProjectResponse handleRequest(CreateProjectRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateProjectResponse response;
		try {
			if (createProject(req.name, req.value)) {
				response = new CreateProjectResponse(req.name);
			} else {
				response = new CreateProjectResponse(req.name, 422);
			}
		} catch (Exception e) {
			response = new CreateProjectResponse("Unable to create project: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}
