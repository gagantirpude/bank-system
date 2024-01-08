package com.bank.system;

import org.mindrot.jbcrypt.BCrypt;

public class HashPassword {
    // * this method will hash the password and return the hashed password.
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // * this method will check if the password is correct.
    // * it will return true if the password is correct.
    public static Boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}