package com.bank.system;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AccountManager {


    private final Scanner scanner;
    private final Connection connection;
    
    public AccountManager(Scanner scanner, Connection connection) {

        this.scanner = scanner;
        this.connection = connection;
    }

    public void getBalance(long accountNumber) {

        try{
            scanner.nextLine();
            System.out.print("Enter your pin :");
            String pin = scanner.nextLine();


            String checkAccountNumber = Query.checkAccountNumber;
            try(PreparedStatement preparedStatement = connection.prepareStatement(checkAccountNumber)){

                preparedStatement.setLong(1,accountNumber);
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    if(rs.next()){
                        String storedPin = rs.getString("pin");
                        //todo: verify pin
                        if(BCrypt.checkpw(pin,storedPin)){
                            //todo: get balance from database column filed
                            System.out.println("Your balance is "+rs.getDouble("balance"));
                        }else{
                            System.out.println("Pin does not match");
                            System.out.println("Please try again");
                        }
                    }else{
                        System.out.println("Account number does not exist");
                        System.out.println("Please try again");
                    }
                }
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public void debit_money(long accountNumber) {
        try{
            scanner.nextLine();
            System.out.print("Enter Amount to debit :");
            double amount = scanner.nextDouble();

            scanner.nextLine();
            System.out.print("Enter your pin :");
            String pin = scanner.nextLine();


            String checkAccountNumber = Query.checkAccountNumber;
            try(PreparedStatement preparedStatement = connection.prepareStatement(checkAccountNumber)){

                preparedStatement.setLong(1,accountNumber);
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    if(rs.next()){
                        String storedPin = rs.getString("pin");
                        //todo: verify pin
                        if(BCrypt.checkpw(pin,storedPin)){
                            //todo: get balance from database column filed
                            String query1 = Query.debitMoney;
                            PreparedStatement preparedStatement1 =  connection.prepareStatement(query1);
                            preparedStatement1.setDouble(1, amount);
                            preparedStatement1.setLong(2, accountNumber);

                            //todo: if rows of balance column affected
                            int rowsAffected = preparedStatement1.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Rs."+amount+" Amount debited successfully");
                            } else {
                                System.out.println("Debit Money Failed!");
                            }
                        }else{
                            System.out.println("Pin does not match");
                            System.out.println("Please try again");
                        }
                    }else{
                        System.out.println("Account number does not exist");
                        System.out.println("Please try again");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


