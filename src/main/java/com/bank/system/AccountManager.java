package com.bank.system;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try {
            scanner.nextLine();
            System.out.print("Enter your pin :");
            String pin = scanner.nextLine();

            String checkAccountNumber = Query.checkAccountNumber;
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkAccountNumber)) {

                preparedStatement.setLong(1, accountNumber);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        String storedPin = rs.getString("pin");
                        // todo: verify pin
                        if (BCrypt.checkpw(pin, storedPin)) {
                            // todo: get balance from database column filed
                            System.out.println("Your Current balance is " + rs.getDouble("balance"));
                        } else {
                            System.out.println("Pin does not match");
                            System.out.println("Please try again");
                        }
                    } else {
                        System.out.println("Account number does not exist");
                        System.out.println("Please try again");
                    }
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void debit_money(long accountNumber) {
        try {
            scanner.nextLine();
            System.out.print("Enter Amount to debit :");
            double amount = scanner.nextDouble();

            scanner.nextLine();
            System.out.print("Enter your pin :");
            String pin = scanner.nextLine();

            String checkAccountNumber = Query.checkAccountNumber;
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkAccountNumber)) {

                preparedStatement.setLong(1, accountNumber);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        String storedPin = rs.getString("pin");
                        // todo: verify pin
                        if (BCrypt.checkpw(pin, storedPin)) {
                            // * Debit Query */
                            String query1 = Query.debitMoney;
                            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                            preparedStatement1.setDouble(1, amount);
                            preparedStatement1.setLong(2, accountNumber);

                            // todo: if rows of balance column affected
                            int rowsAffected = preparedStatement1.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Rs." + amount + " Amount debited successfully");
                            } else {
                                System.out.println("Debit Money Failed!");
                            }
                        } else {
                            System.out.println("Pin does not match");
                            System.out.println("Please try again");
                        }
                    } else {
                        System.out.println("Account number does not exist");
                        System.out.println("Please try again");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void credit_money(long accountNumber) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount to credit : ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter your pin :");
        String pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            String checkAccountNumber = Query.checkAccountNumber;
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkAccountNumber)) {

                preparedStatement.setLong(1, accountNumber);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        String storedPin = rs.getString("pin");
                        // todo: verify pin
                        if (BCrypt.checkpw(pin, storedPin)) {
                            // todo: get balance from database column filed

                            // *credit Query
                            String query1 = Query.creditMoney;
                            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                            preparedStatement1.setDouble(1, amount);
                            preparedStatement1.setLong(2, accountNumber);

                            // todo: if rows of balance column affected
                            int rowsAffected = preparedStatement1.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Rs." + amount + " Amount Credit successfully");
                                connection.commit();
                                connection.setAutoCommit(true);
                            } else {
                                System.out.println("Cedit Money Failed!");
                                connection.rollback();
                                connection.setAutoCommit(true);
                            }
                        } else {
                            System.out.println("Pin does not match");
                            System.out.println("Please try again");
                        }
                    } else {
                        System.out.println("Account number does not exist");
                        System.out.println("Please try again");
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        connection.setAutoCommit(true);

    }

    public void transfer_money(long accountNumber) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number Please : ");
        long receiverAccountNumber = scanner.nextLong();

        System.out.print("Enter Amount you want to Transfer : ");
        double amount = scanner.nextDouble();

        scanner.nextLine();
        System.out.print("Enter Your Security Pin : ");
        String pin = scanner.nextLine();

        try {
            // connection auto commit off

            // * First Verify User Account Number and Pin */
            String checkAccountNumber = Query.checkAccountNumber;
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkAccountNumber)) {
                preparedStatement.setLong(1, accountNumber);

                // get data from database
                ResultSet rs = preparedStatement.executeQuery();
                System.out.println(rs);
                if (rs.next()) {

                    // * verify pin */
                    String storedPin = rs.getString("pin");
                    if (BCrypt.checkpw(pin, storedPin)) {

                        // now check Balance of Account Number
                        String checkBalance = Query.checkBalance;
                        try (PreparedStatement preparedStatement1 = connection.prepareStatement(checkBalance)) {
                            preparedStatement1.setLong(1, accountNumber);

                            ResultSet rs1 = preparedStatement1.executeQuery();
                            if (rs1.next()) {
                                // get balance from database column file
                                double balance = rs1.getDouble("balance");
                                // * if balance is greater than amount then only transfer money
                                if (balance > amount) {
                                    // * Debit Query */
                                    String query1 = Query.debitMoney;
                                    PreparedStatement preparedStatement2 = connection.prepareStatement(query1);
                                    preparedStatement2.setDouble(1, amount);
                                    preparedStatement2.setLong(2, accountNumber);

                                    // * Credit Query */
                                    String query2 = Query.creditMoney;
                                    PreparedStatement preparedStatement3 = connection.prepareStatement(query2);
                                    preparedStatement3.setDouble(1, amount);
                                    preparedStatement3.setLong(2, receiverAccountNumber);
                                    preparedStatement3.executeUpdate();

                                    int rowsAffected = preparedStatement2.executeUpdate();
                                    if (rowsAffected > 0) {
                                        System.out.println("Rs." + amount + " Amount Transfer successfully");

                                    } else {
                                        System.out.println("Transfer Failed!");
                                    }
                                } else {
                                    System.out.println("Insufficient Balance");
                                    System.out.println("Please try again");

                                    return;

                                }
                            }

                        }

                    } else {
                        System.out.println("Pin does not match");
                        System.out.println("Please try again");
                    }

                } else {
                    System.out.println("Account number does not exist");
                    System.out.println("Please try again");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
