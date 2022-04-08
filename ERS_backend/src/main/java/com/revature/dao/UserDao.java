package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public UserDao() {
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException, IOException {
        try (Connection con = ConnectionUtility.getConnection()) { // Automatically closes the Connection object
            String sql = "SELECT ers_users.ers_user_id, ers_users.user_role_id, ers_users.ers_username, ers_users.ers_password, " +
                    "ers_user_roles.user_role " +
                    "FROM ers_users " +
                    "INNER JOIN ers_user_roles " +
                    "ON ers_users.user_role_id = ers_user_roles.ers_user_role_id " +
                    "WHERE ers_users.ers_username = ? AND ers_users.ers_password = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { // if there is actually a user to iterate over
                int userId = rs.getInt("ers_user_id");
                String un = rs.getString("ers_username");
                String pw = rs.getString("ers_password");
                int role = rs.getInt("user_role_id");

                return new User(userId, un, pw, role);
            }

            return null;
        }
    }
    public static String getUserNames(int userId)  throws SQLException, FileNotFoundException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT ers_users.user_first_name, ers_users.user_last_name FROM ers_users WHERE ers_users.ers_user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            String firstName;
            String lastName;

            if (rs.next()) {
                firstName = rs.getString("user_first_name");
                lastName = rs.getString("user_last_name");
                return firstName + " " + lastName;
            }
        }
        return null;
    }


}
