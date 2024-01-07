package com.bank.system;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;

public class HashPassword {
    //this method will hash the password and return the hashed password.
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    //this method will check if the password is correct.
    //it will return true if the password is correct.
    public static Boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

//    public static void loginUser(String username, String password) {
//        try {
//            Connection connection = Database.connect();
//            String query = "SELECT password FROM users WHERE username = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, username);
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String storedHashedPassword = resultSet.getString("password");
//                        if (BCrypt.checkpw(password, storedHashedPassword)) {
//                            System.out.println("Login successful!");
//                        } else {
//                            System.out.println("Incorrect password!");
//                        }
//                    } else {
//                        System.out.println("User not found!");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}