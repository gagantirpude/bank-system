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

                System.out.println("Open Account");

                scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Initial Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter Security Pin: ");
                String pin = scanner.nextLine();



                //todo: hashPassword method
                String hashPin = HashPassword.hashPassword(pin);
                System.out.println(hashPin);

                //todo:accountNumber method
                long accountNumber = generateAccountNumber(email);
                System.out.println(accountNumber);

                String openAccountQuery = Query.openAccount;

                System.out.println("open Account 1");
                //todo: dynamic query from PreparedStatement class
                PreparedStatement preparedStatement = connection.prepareStatement(openAccountQuery);

                System.out.println("open Account 2");
                //todo: setString method from PreparedStatement class
                preparedStatement.setString(3, email);
                preparedStatement.setString(2, name);
                preparedStatement.setDouble(4, amount);
                preparedStatement.setString(5, hashPin);
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.executeUpdate();

                System.out.println("open Account 3");
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    System.out.println("Account Opened Successfully!");
                    return resultSet.getLong(1);
                }else{
                    System.out.println("Account Opened Failed!");
                    return 0;
                }
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


    public long generateAccountNumber(String email){
        long accountNumber = 0;
        try {

            System.out.println("Generate Account Number 1");
            //check if account number exists

            String query = Query.checkAccountEmail;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            System.out.println("Generate Account Number 2");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Generate Account Number 3");
                System.out.println("Account Number Exists");
               return accountNumber = resultSet.getLong(1);
            }else{
                //todo: generateAccountNumber
                System.out.println("Generate Account Number 4");
                accountNumber = Long.parseLong(GenerateAccountNumber.generateBankAccountNumber());
                System.out.println(accountNumber);
                System.out.println("Generated Account Number");
                return accountNumber;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return accountNumber;
    }


}
