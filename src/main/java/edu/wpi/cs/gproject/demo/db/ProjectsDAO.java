package edu.wpi.cs.gproject.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.gproject.demo.model.Project;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Projects" then it must be "Projects" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class ProjectsDAO { 

	java.sql.Connection conn;
	LambdaLogger logger;
	
	final String tblName = "ProjectSchema";   // Exact capitalization
	final String taskTblName = "TaskSchema";
	final String mTblName = "Mapping";
	
    public ProjectsDAO(LambdaLogger logger) {
    	this.logger = logger;
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Project getProject(String name) throws Exception {
        
        try {
            Project project = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                project = generateProject(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return project;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting Project: " + e.getMessage());
        }
    }
    
    public boolean updateProject(Project project) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE name=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, project.value);
            ps.setString(2, project.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean updateProjectCompletion(String name, double value) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE name=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, value);
            ps.setString(2, name);
        	
            int numAffected = ps.executeUpdate();
            ps.close();
            logger.log("Project updated (possibly)");
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update project value: " + e.getMessage());
        }
    }
    
    public boolean deleteProject(Project project) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, project.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert Project: " + e.getMessage());
        }
    }


    public boolean addProject(Project project) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, project.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Project c = generateProject(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
            ps.setString(1,  project.name);
            ps.setDouble(2,  project.value);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert Project: " + e.getMessage());
        }
    }

    public List<Project> getAllProjects() throws Exception {
        
        List<Project> allProjects = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Project c = generateProject(resultSet);
                allProjects.add(c);
            }
            resultSet.close();
            statement.close();
            return allProjects;

        } catch (Exception e) {
            throw new Exception("Failed in getting Projects: " + e.getMessage());
        }
    }
    
    private Project generateProject(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("name");
        Double value = resultSet.getDouble("value");
        return new Project (name, value);
    }
    
    public boolean deleteTasks(Project project) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + taskTblName + " WHERE name = ?;");
            ps.setString(1, project.name);
            int numAffected = -1;
            numAffected = ps.executeUpdate();
            ps.close();
            
          //  return (numAffected == 1);
            return (numAffected != -1 );

        } catch (Exception e) {
            throw new Exception("Failed to remove tasks: " + e.getMessage());
        }
    }
    public boolean deleteTeammates(Project project) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + mTblName + " WHERE name = ?;");
            ps.setString(1, project.name);
            int numAffected = 0;
            numAffected = ps.executeUpdate();
            ps.close();
            
          //  return (numAffected == 1);
            return (numAffected !=-1 );

        } catch (Exception e) {
            throw new Exception("Failed to remove tasks: " + e.getMessage());
        }
    }

}
