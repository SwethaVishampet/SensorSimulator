/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsimulator;

//import com.mysql.jdbc.*;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vgswetha92
 */
public class DatabaseHandler {
    
    

    //Strings for database connection
    String host = "jdbc:mysql://localhost:3306";
    String dbName = "green_techniques";
    String username = "root";
    String password = "password";
    String connectString = host;//+"/"+dbName;
    //related to databse connection
    java.sql.Connection connection = null;
    Statement stat = null;
    

    public DatabaseHandler(String dbName) {
        this.dbName = dbName;
    }

    public void connectToAndQueryDatabase() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectString, username, password);
            stat = connection.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS green_techniques";
            stat.executeUpdate(sql);
        } catch (SQLException se) {
            if (se.getErrorCode() == 1007) {// Database already exists error
                System.out.println(se.getMessage());
            } else {// Some other problems, e.g. Server down, no permission, etc
                se.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
    }
    
    public void insertIntoReadings  (String sensorid, Date time , int reading) throws SQLException{
        System.out.println("Entered");
         try{
            stat = connection.createStatement();
            
          String query = "INSERT INTO `green_techniques`.`Readings` (`sensorid`, `time`, `reading` ) VALUES ('" + sensorid + "', '" + time + "', '" + reading + "')";
         
          stat.execute(query);
     } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
