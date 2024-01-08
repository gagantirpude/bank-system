package com.bank.system;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Connection connection = Database.connect();
            User user = new User(scanner);
            Account account = new Account(scanner, connection);
            AccountManager accountManager = new AccountManager(scanner, connection);

            String email;
            long accountNumber;

            while (true) {

                System.out.println("\n\n ----Bank Management System ----- \n");

                System.out.println("1. New user");
                System.out.println("2. Login user");
                // System.out.println("3. Bank Manager Login");
                // System.out.println("4. Admin Account");
                System.out.println("5. Exit");
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        user.registerUser();
                        break;
                    case 2:
                        email = user.loginUser();
                        // System.out.println(email);

                        // todo: check if user exists
                        if (email != null) {
                            // System.out.println("User logged in successfully");

                            // todo: check if user have account exists
                            if (account.account_exit(email)) {
                                System.out.println(" ");
                                System.out.println("1. Open a new Bank Account ");
                                System.out.println("2. Exit");

                                System.out.print("\nEnter your choice : ");
                                if (scanner.nextInt() == 1) {
                                    accountNumber = account.open_account(email);
                                    if (accountNumber != 0L) {
                                        System.out.println("\n Account Created Successfully");
                                        System.out.println("\n Your Account Number is: " + accountNumber);
                                    }
                                }
                            } else {
                                System.out.println("\n...Welcome To Our Bank...\n");
                                accountNumber = account.getAccount_number(email);

                                int choice2 = 0;
                                while (choice2 != 5) {
                                    System.out.println();
                                    System.out.println("1. Debit Money");
                                    System.out.println("2. Credit Money");
                                    System.out.println("3. Transfer Money");
                                    System.out.println("4. Check Balance");
                                    System.out.println("5. Log Out");
                                    System.out.print("\nEnter your choice : ");
                                    choice2 = scanner.nextInt();
                                    switch (choice2) {
                                        case 1:
                                            System.out.println("Debit Money");
                                            accountManager.debit_money(accountNumber);
                                            break;
                                        case 2:
                                            System.out.println("Credit Money");
                                            accountManager.credit_money(accountNumber);
                                            break;
                                        case 3:
                                            System.out.println("Transfer Money");
                                            accountManager.transfer_money(accountNumber);
                                            break;
                                        case 4:
                                            System.out.println("Check Balance");
                                            accountManager.getBalance(accountNumber);
                                            break;
                                        case 5:
                                            System.out.println(email + " Logged Out Successfully");
                                            Thread.sleep(5000);
                                            break;
                                        default:
                                            System.out.println("Enter Valid Choice!");
                                            break;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Invalid Credentials from Login");
                        }
                        break;
                    case 3:
                        // accountManager.depositMoney();
                        System.out.println("Bank Manager Account");
                        break;
                    case 4:
                        // account.deleteAccount();
                        System.out.println("Admin Account");
                        break;
                    case 5:
                        System.out.println("Thank You For Visit");
                        System.out.println("Exiting...");
                        Thread.sleep(5000);
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }
}