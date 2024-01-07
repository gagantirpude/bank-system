package com.bank.system;

public class Query {


    public static String createDB = "CREATE DATABASE IF NOT EXISTS";
    public static String showDB = "SHOW DATABASES LIKE";
    public static String useDB = "USE";


    // User Table Queries
    public static String createUserTable = "CREATE TABLE IF NOT EXISTS users (id INT(11) NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, email VARCHAR(45) NOT NULL, password VARCHAR(100) NOT NULL, PRIMARY KEY (id))"; 

    public static String showUserTable = "SHOW TABLES LIKE";
    public static String selectUserTable = "SELECT * FROM users";
    public static String insertUserTable = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

    public static String checkUser = "SELECT * FROM users WHERE email = ?";


    //todo: Account Table Queries
    public static String createAccountTable = "CREATE TABLE IF NOT EXISTS accounts (  account_number bigint(10) NOT NULL PRIMARY KEY, name varchar(50) NOT NULL, email varchar(50) NOT NULL UNIQUE, balance decimal(10,2) NOT NULL, pin varchar(100) NOT NULL)";

    public static String showAccountTable = "SHOW TABLES LIKE";

    public static String checkAccountEmail = "SELECT * FROM accounts WHERE email = ?";

    public static String openAccount = "INSERT INTO accounts (account_number, name, email, balance, pin) VALUES (?, ?, ?, ?, ?)";

    public static String getAccountNumber = "SELECT account_number FROM accounts WHERE email = ? ORDER BY account_number DESC LIMIT 1";
//    public static String checkAccountNumber = "SELECT * FROM accounts WHERE account_number = ? OR email =


    //todo: Get balance
    public static String getBalance = "SELECT balance FROM accounts WHERE account_number = ? AND pin = ?";

    public static String checkAccountNumber = "SELECT * FROM accounts WHERE account_number = ?";

    public static String debitMoney = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

}

