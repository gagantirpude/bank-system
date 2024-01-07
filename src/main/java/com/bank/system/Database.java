package com.bank.system;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Database {
    
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
    public static final String DB_NAME = "bank_db";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";

    public static Connection connect() {
        
        Connection connection = null;
        Statement statement = null;
        
        try {
            //*1 Load JDBC Driver */
            // Class.forName("com.mysql.jdbc.Driver");

            //*2 Create Connection */
            connection = DriverManager.getConnection(DB_URL+DB_NAME, DB_USER, DB_PASSWORD);

            //*3 Create Statement for Execute Query */
            statement = connection.createStatement();

           
            //* Get Query form Query Class*/
            String sql = Query.showDB + " '" + DB_NAME + "'";
            
            //*4 Process Result */
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                System.out.println("Database '" + DB_NAME + "' exists.");
            } else {
                System.out.println("Database '" + DB_NAME + "' does not exist.");
                statement.executeUpdate(Query.createDB + " " + DB_NAME);
                System.out.println("Database '" + DB_NAME + "' created.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection;
    }

}
