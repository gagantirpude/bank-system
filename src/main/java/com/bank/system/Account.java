package com.bank.system;

import java.sql.*;
import java.util.Scanner;

import static com.bank.system.Database.DB_NAME;

public class Account {


    private Scanner scanner;
    private Connection connection;
    
    public Account( Scanner scanner, Connection connection  ) {
        this.scanner = scanner;
        this.connection = connection;
    }



    public long open_account(String email) {

        try{
            if(!account_exit(email)){
                System.out.println("Account Not Exists Open New Account");

                return 0;
            }else{
                System.out.println(" Open Account already exists!");
                return 1;
            }


        }catch (Exception e){
            System.out.println(e);
        }




        return 0;
    }


    public boolean account_exit(String email) {
        try {

            System.out.println("Account Exists");

            //todo: createStatement method from Connection class
            Statement statement = connection.createStatement();

            //todo: Show Account Table Query from Query class.
            String query1 = Query.showAccountTable + " 'accounts'";

            //todo: executeQuery method from Statement class
            ResultSet resultSet = statement.executeQuery(query1);

            //todo: if user table exists, check user from database
            if (resultSet.next()) {
                System.out.println("Database Account table already exists.");
                //todo: checkAccountEmail query from Query class
                String query = Query.checkAccountEmail;

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet2 = preparedStatement.executeQuery();
                return  (resultSet2.next());

            } else {
                System.out.println("In Database Account table does not exist.");

                //todo: Create Account Table Query from Query class.
                String accountTable = Query.createAccountTable;
                //todo: createAccountTable query from Query class
                statement.executeUpdate(accountTable);

                //todo: showUserTable query from Query class
                System.out.println("Account table created in Database '"+DB_NAME+"' successfully");
                return false;
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return true;
    }


}
