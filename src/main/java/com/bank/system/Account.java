package com.bank.system;

import java.sql.*;
import java.util.Scanner;

import static com.bank.system.Database.DB_NAME;

public class Account {

    private final Scanner scanner;
    private final Connection connection;

    public Account(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public long open_account(String email) {

        try {
            if (account_exit(email)) {

                System.out.println("Please Enter Your Detail To Create Bank Account...");

                scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Security Pin: ");
                String pin = scanner.nextLine();
                System.out.print("Enter Initial Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                // todo: hashPassword method
                String hashPin = HashPassword.hashPassword(pin);
                // System.out.println(hashPin);

                // todo:accountNumber method
                long accountNumber = generateAccountNumber(email);
                // System.out.println(accountNumber);

                String openAccountQuery = Query.openAccount;

                // todo: dynamic query from PreparedStatement class
                PreparedStatement preparedStatement = connection.prepareStatement(openAccountQuery);

                // todo: setString method from PreparedStatement class
                preparedStatement.setString(3, email);
                preparedStatement.setString(2, name);
                preparedStatement.setDouble(4, amount);
                preparedStatement.setString(5, hashPin);
                preparedStatement.setLong(1, accountNumber);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return accountNumber;
                } else {
                    throw new RuntimeException("Account Creation failed!!");
                }
            } else {
                System.out.println(email + " Open Account already exists!");
                return 1;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return 0;
    }

    public boolean account_exit(String email) {
        try {
            // System.out.println("Account Exists");

            // todo: createStatement method from Connection class
            Statement statement = connection.createStatement();

            // todo: Show Account Table Query from Query class.
            String query1 = Query.showAccountTable + " 'accounts'";

            // todo: executeQuery method from Statement class
            ResultSet resultSet = statement.executeQuery(query1);

            // todo: if user table exists, check user from database
            if (resultSet.next()) {
                // System.out.println("Database Account table already exists.");
                // todo: checkAccountEmail query from Query class
                String query = Query.checkAccountEmail;

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet2 = preparedStatement.executeQuery();
                if (resultSet2.next()) {
                    return false;
                } else {
                    return true;
                }

            } else {
                // System.out.println("In Database Account table does not exist.");

                // todo: Create Account Table Query from Query class.
                String accountTable = Query.createAccountTable;
                // todo: createAccountTable query from Query class
                statement.executeUpdate(accountTable);

                // todo: showUserTable query from Query class
                System.out.println("Account table created in Database " + DB_NAME + " successfully");
                return true;
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return false;
    }

    public long generateAccountNumber(String email) {
        long accountNumber = 0;
        try {
            // check if account number exists

            String query = Query.checkAccountEmail;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountNumber = resultSet.getLong(1);
                System.out.println("Your Account Number is : " + accountNumber);
            } else {
                // todo: generateAccountNumber
                accountNumber = Long.parseLong(GenerateAccountNumber.generateBankAccountNumber());
            }
            return accountNumber;

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return accountNumber;
    }

    public long getAccount_number(String email) {
        try {
            String query = Query.getAccountNumber;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                return 0;
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return 0;
    }
}
