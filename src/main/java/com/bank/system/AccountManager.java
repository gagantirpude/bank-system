package com.bank.system;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}


