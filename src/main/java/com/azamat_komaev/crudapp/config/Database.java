package com.azamat_komaev.crudapp.config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Database instance;
    private Connection connection = null;
    private String url;
    private String username;
    private String password;

    private Database() {
        setUpProperties();
    }

    private File getPropertiesFile() {
        File folder = new File("").getAbsoluteFile();

        for (int i = 0; i < 7; i++) {
            folder = folder.getParentFile();
        }

        return new File(folder.getAbsolutePath() + "/liquibase.properties");
    }

    private void setUpProperties() {
        Properties properties = new Properties();

        try (
            InputStream in = new FileInputStream(getPropertiesFile());
        ) {
            properties.load(in);

            this.url = properties.getProperty("url");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url + "?user=" + username + "&password=" + password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
