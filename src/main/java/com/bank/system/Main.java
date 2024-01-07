package com.bank.system;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Connection connection = Database.connect();
            User user = new User(scanner);
            Account account = new Account( scanner, connection);
            AccountManager accountManager = new AccountManager( scanner);

            String email;
            long accountNumber;


            while (true) {
                System.out.println("1. Create a new user");
                System.out.println("2. Login user");
                System.out.println("3. Deposit money");
                System.out.println("4. Withdraw money");
                System.out.println("5. Transfer money");
                System.out.println("6. Show user details");
                System.out.println("7. Show account details");
                System.out.println("8. Show all accounts");
                System.out.println("9. Show all users");
                System.out.println("10. Delete user");
                System.out.println("11. Delete account");
                System.out.println("12. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        user.registerUser();
                        break;
                    case 2:
                        email = user.loginUser();
                        //System.out.println(email);

                        //todo: check if user exists
                        if(email != null) {
                            System.out.println("User logged in successfully");

                            //todo: check if user have account exists
                            if(!account.account_exit(email)) {
                                System.out.println(" \n 1. Open a new Bank Account ");
                                System.out.println("2. Exit");
                                if (scanner.nextInt() == 1) {
                                    accountNumber = account.open_account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + accountNumber);
                                } else {
                                    break;
                                }
                            }else{
//                                accountNumber = account.getAccount_number(email);
//                                int choice2 = 0;
//                                while (choice2 != 5) {
//                                    System.out.println();
//                                    System.out.println("1. Debit Money");
//                                    System.out.println("2. Credit Money");
//                                    System.out.println("3. Transfer Money");
//                                    System.out.println("4. Check Balance");
//                                    System.out.println("5. Log Out");
//                                    System.out.println("Enter your choice: ");
//                                    choice2 = scanner.nextInt();
//                                    switch (choice2) {
//                                        case 1:
//                                            accountManager.debit_money(accountNumber);
//                                            break;
//                                        case 2:
//                                            accountManager.credit_money(accountNumber);
//                                            break;
//                                        case 3:
//                                            accountManager.transfer_money(accountNumber);
//                                            break;
//                                        case 4:
//                                            accountManager.getBalance(accountNumber);
//                                            break;
//                                        case 5:
//                                            System.out.println("Logged Out Successfully");
//                                            break;
//                                        default:
//                                            System.out.println("Enter Valid Choice!");
//                                            break;
//                                    }
//                                }
//
                                System.out.println("Account Features are not available right now");
                            }
                        }else{
                            System.out.println("Invalid Credentials");
                        }
                        break;
                    case 3:
                        // accountManager.depositMoney();
                        System.out.println("Money deposited successfully");
                        break;
                    case 4:
                        // accountManager.withdrawMoney();
                        System.out.println("Money withdrawn successfully");
                        break;
                    case 5:
                        // accountManager.transferMoney();
                        System.out.println("Money transferred successfully");
                        break;
                    case 6:
                        // user.showUserDetails();
                        System.out.println("User details");
                        break;
                    case 7:
                        // account.showAccountDetails();
                        System.out.println("Account details");
                        break;
                    case 8:
                        // account.showAllAccounts();
                        System.out.println("All accounts");
                        break;
                    case 9:
                        // user.showAllUsers();
                        System.out.println("All users");
                        break;
                    case 10:
                        // user.deleteUser();
                        System.out.println("User deleted successfully");
                        break;
                    case 11:
                        // account.deleteAccount();
                        System.out.println("Account deleted successfully");
                        break;
                    case 12:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

    }
}