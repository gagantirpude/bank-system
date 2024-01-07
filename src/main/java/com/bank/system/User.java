package com.bank.system;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//import static com.bank.system.Database.DB_NAME;


public class User {

    private final Scanner scanner;

    public User( Scanner scanner) {
        
        this.scanner = scanner;
    }

    public void registerUser() {

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        //todo: hashPassword method
        String hashPassword =HashPassword.hashPassword(password);

        //todo: checkUser method
        // if user already exists, print message and return from method. 
        if (checkUser(email)) {
            System.out.println("User Already Exists for this Email Address...");
        } else {
            try {
                //todo: connect method from Database class
                Connection connection = Database.connect();

                //todo: insertUserTable query from Query class
                String query = Query.insertUserTable;

                //todo: dynamic query from PreparedStatement class
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, hashPassword);
                preparedStatement.executeUpdate();
                int affectedRows = preparedStatement.executeUpdate();

                //todo: if affectedRows > 0, print message
                if (affectedRows > 0) {
                    System.out.println("Registration Successfull!");
                } else {
                    System.out.println("Registration Failed!");
                }
            } catch (SQLException e) {
                e.fillInStackTrace();
            }
        }

    }


     //todo: checkUser method
    public Boolean checkUser(String email) {
        try {
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();

            //System.out.println("check user");
            //todo: Create User Table Query from Query class.
            String userTable = Query.createUserTable;


            //todo: Show User Table Query from Query class.
            String query1 = Query.showUserTable + " 'users'";

            //todo: executeQuery method from Statement class
            ResultSet resultSet = statement.executeQuery(query1);

            //todo: if user table exists, check user from database
            if (resultSet.next()) {
                //System.out.println("Database users table already exists.");
                //todo: checkUser query from Query class
                String query = Query.checkUser;

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet2 = preparedStatement.executeQuery();
                return  (resultSet2.next());

            } else {
                //System.out.println("In Database users table does not exist.");

                //todo: createUserTable query from Query class
                statement.executeUpdate(userTable);

                //todo: showUserTable query from Query class
                //System.out.println("User table created in Database '"+DB_NAME+"' successfully");
                return false;
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return true;
    }


    //todo: User Login method
    public String loginUser() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        //todo: hashPassword method
        String hashPassword =HashPassword.hashPassword(password);


        try {
            Connection connection = Database.connect();
            String query = Query.checkUser;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        //todo : get password from database
                        String storedHashedPassword = resultSet.getString("password");

                        //todo: verifyPassword method
                        if (BCrypt.checkpw(password, storedHashedPassword)) {
                            System.out.println("Login successful!");
                            return email;
                        } else {
                            System.out.println("Incorrect password!");
                        }
                    } else {
                        System.out.println("User not found!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     return null;
    }

}
