package com.bank.system;

import java.util.Random;

public class GenerateAccountNumber {

    public static String generateBankAccountNumber() {
        // Specify the length of the bank account number
        int accountNumberLength = 12;

        // Define the range of digits (0-9) for the account number
        int minDigit = 0;
        int maxDigit = 9;

        // Use StringBuilder to efficiently build the account number
        StringBuilder accountNumber = new StringBuilder(accountNumberLength);
        Random random = new Random();

        // Generate each digit of the account number
        for (int i = 0; i < accountNumberLength; i++) {
            int randomDigit = random.nextInt(maxDigit - minDigit + 1) + minDigit;
            accountNumber.append(randomDigit);
        }

        return accountNumber.toString();
    }
}
