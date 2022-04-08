package com.revature.utility;

import org.postgresql.Driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtility {

    public static Connection getConnection() throws SQLException, FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream("connection.prop");
            Properties p = new Properties();
            p.load(fis);
            String url = (String) p.get("URL");
            String username = (String) p.get("USER");
            String password = (String) p.get("PASSWORD");

            DriverManager.registerDriver(new Driver());

            Connection connection = DriverManager.getConnection(url, username, password);

            return connection;
        } catch (IOException e) {
            throw new FileNotFoundException("Connection file was not found. Message: " + e.getMessage());
        }
    }
}

