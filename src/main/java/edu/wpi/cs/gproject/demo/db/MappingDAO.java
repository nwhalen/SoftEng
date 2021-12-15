package edu.wpi.cs.gproject.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.gproject.demo.model.Mapping;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Projects" then it must be "Projects" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class MappingDAO { 

	java.sql.Connection conn;
	LambdaLogger logger;
	
	final String tblName = "Mapping";   // Exact capitalization

    public MappingDAO(LambdaLogger logger) {
    	this.logger = logger;
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Mapping getMapping(String mappingID) throws Exception {
        
        try {
            Mapping mapping = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE mappingID=?;");
            ps.setString(1,  mappingID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                mapping = generateMapping(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return mapping;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting Mapping: " + e.getMessage());
        }
    }
 /*   
    public boolean updateMapping(Mapping mapping) throws Exception {
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
    
   */
    public boolean deleteMapping(Mapping mapping) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE mappingID = ? AND name = ?;");
            ps.setString(1, mapping.mappingID);
            ps.setString(2, mapping.name); //CHECK IF THIS IS ACTUALLY NECESRY
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert Mapping: " + e.getMessage());
        }
    }


    public boolean addMapping(Mapping mapping) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE mappingID = ?;");
            ps.setString(1, mapping.mappingID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Mapping c = generateMapping(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (taskID,teammate,name,mappingID) values(?,?,?,?);");
            ps.setString(1,mapping.taskID);
            ps.setString(2,mapping.teammate);
            ps.setString(3,mapping.name);
            ps.setString(4,mapping.mappingID);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert Project: " + e.getMessage());
        }
    }

    public List<Mapping> getAllMappings(String name) throws Exception {
        
        List<Mapping> allMappings = new ArrayList<>();
        try {
//            Statement statement = conn.createStatement();
//            String query = "SELECT * FROM " + tblName + ";";
//            ResultSet resultSet = statement.executeQuery(query);
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Mapping c = generateMapping(resultSet);
                allMappings.add(c);
            }
            resultSet.close();
            ps.close();
            return allMappings;

        } catch (Exception e) {
            throw new Exception("Failed in getting Mappings: " + e.getMessage());
        }
    }
    
    private Mapping generateMapping(ResultSet resultSet) throws Exception {
        String taskID  = resultSet.getString("taskID");
        String teammate = resultSet.getString("teammate");
        String name = resultSet.getString("name");
        String mappingID = taskID + teammate;
        return new Mapping (taskID, teammate, name, mappingID);
    }

}
