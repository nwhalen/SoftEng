package edu.wpi.cs.gproject.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.gproject.demo.model.Task;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Projects" then it must be "Projects" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class TaskDAO { 

	java.sql.Connection conn;
	LambdaLogger logger;
	
	final String tblName = "TaskSchema";   // Exact capitalization

    public TaskDAO(LambdaLogger logger) {
    	this.logger = logger;
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Task getTask(String taskName) throws Exception {
        
        try {
            Task task = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE taskName=?;");
            ps.setString(1,  taskName);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                task = generateTask(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return task;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting Task: " + e.getMessage());
        }
    }
   
    public boolean updateTask(Task task) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET completed=? WHERE taskPK=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
        	if(task.completed == 1) {
        		ps.setInt(1, 0);
        	} else ps.setInt(1, 1);
        	
            ps.setString(2, task.taskPK);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update task: " + e.getMessage());
        }
    }
    
   
    public boolean deleteTask(Task task) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE taskPK = ?;");
            ps.setString(1, task.taskPK);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert task: " + e.getMessage());
        }
    }


    public boolean addTask(Task task) throws Exception {
        try {
        	logger.log("taskDAO adding task: " + task);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE taskPK = ?;");
            ps.setString(1, task.taskPK);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Task c = generateTask(resultSet);
                resultSet.close();
               // logger.log(task + " already exists");
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (taskID,taskName,completed,name,parentID,taskPK) values(?,?,?,?,?,?);");
            ps.setString(1,task.taskID);
            ps.setString(2,task.taskName);
            ps.setInt(3,task.completed);
            ps.setString(4,task.name);
            ps.setString(5,"NONE");
            ps.setString(6,task.taskPK);
            ps.execute();
            logger.log(task + "added");
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert Task: " + e.getMessage());
        }
    }

    public List<Task> getAllTasks(String name) throws Exception {
        
        List<Task> allTasks = new ArrayList<>();
        try {
//            Statement statement = conn.createStatement();
//            String query = "SELECT * FROM " + tblName + ";";
//            ResultSet resultSet = statement.executeQuery(query);
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Task c = generateTask(resultSet);
                allTasks.add(c);
            }
            resultSet.close();
            ps.close();
            return allTasks;

        } catch (Exception e) {
            throw new Exception("Failed in getting Tasks: " + e.getMessage());
        }
    }
    
    private Task generateTask(ResultSet resultSet) throws Exception {
        String taskID  = resultSet.getString("taskID");
        String taskName = resultSet.getString("taskName");
        int completed = resultSet.getInt("completed"); //maybe problem hear
        String name = resultSet.getString("name");
        String taskPK = resultSet.getString("taskPK");
        return new Task (taskID, taskName, completed, name, taskPK);
    }
    
    public int calculateTaskID(String name) throws Exception {
    	try {
//    	 Task task = null;
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
         ps.setString(1, name);
         ResultSet resultSet = ps.executeQuery();
         int maxID = 1;
         
         while (resultSet.next()) {
             Task t = generateTask(resultSet);
             
           
             if(Integer.parseInt(t.taskID) >= maxID) {
        	// if (Integer.parseInt(t.taskID) >= taskID) {
        		 maxID = Integer.parseInt(t.taskID) + 1;
        	 }
             
            
         }
         resultSet.close();
         ps.close();
         return maxID;
    	} catch (Exception e) {
            throw new Exception("Failed in getting Tasks: " + e.getMessage());
        }

    }
    
   public double totalTasks(String name) throws Exception {
	   try {
		   PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
		   ps.setString(1, name);
		   ResultSet resultSet = ps.executeQuery();
		   int totalTasks = 0;
		   
		   while (resultSet.next()) {
			   Task t = generateTask(resultSet);
			   totalTasks++;
		   }
		   
		   resultSet.close();
	         ps.close();
	         return totalTasks;
	   } catch (Exception e) {
		   throw new Exception("Failed in calculating total tasks: " + e.getMessage());
	   }
   }
   public double completedTasks(String name) throws Exception {
	   try {
		   PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
		   ps.setString(1, name);
		   ResultSet resultSet = ps.executeQuery();
		   int completedTasks = 0;
		   
		   while (resultSet.next()) {
			   Task t = generateTask(resultSet);
			   if (t.completed == 1) {
			   completedTasks++;
			   }
		   }
		   
		   resultSet.close();
	         ps.close();
	         return completedTasks;
	   } catch (Exception e) {
		   throw new Exception("Failed in calculating total tasks: " + e.getMessage());
	   }
   }
}
