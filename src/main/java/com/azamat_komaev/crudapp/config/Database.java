package com.azamat_komaev.crudapp.config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Database instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public Database() {
        setUpProperties();
    }

    private void setUpProperties() {
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (
            InputStream in = classloader.getResourceAsStream("liquibase.properties");
        ) {
            properties.load(in);

            this.url = properties.getProperty("url");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");

            this.connection = DriverManager.getConnection(url + "?user=" + username + "&password=" + password);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
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
