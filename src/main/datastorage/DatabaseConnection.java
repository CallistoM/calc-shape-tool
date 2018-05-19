package main.datastorage;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {

    private Connection connection;

    // holds connection information
    private Statement statement;

    public DatabaseConnection() {
        connection = null;
        statement = null;
    }


    /**
     * open connection to database
     * @return boolean
     */
    public boolean openConnection() {

        boolean result;

        if (connection == null) {
            try {

                Properties info = new Properties();

                // set properties
                info.setProperty("ssl", "true");

                String url = "jdbc:mysql://127.0.0.1/vat_tool";
                Properties props = new Properties();
                props.setProperty("user", "root");
                props.setProperty("password", "");
                props.setProperty("ssl", "false");
                props.getProperty("verifyServerCertificate", "false");
                Connection conn = DriverManager.getConnection(url, props);


                // Try to create a connection with the library main
                connection = conn;

                if (connection != null) {
                    statement = connection.createStatement();
                }

                result = true;
            } catch (SQLException e) {
                result = false;
            }
        } else {
            // a connection has already been made
            result = true;
        }

        return result;
    }


    /**
     * check if connection is open
     *
     * @return boolean
     */
    public boolean connectionIsOpen() {
        boolean open = false;

        if (connection != null && statement != null) {
            try {
                open = !connection.isClosed() && !statement.isClosed();
            } catch (SQLException e) {
                open = false;
            }
        }
        // else no valid connection.

        return open;
    }


    /**
     * close connection
     */
    public void closeConnection() {
        try {
            statement.close();

            // Close the connection
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * executre select statement
     * @param query string
     * @return result
     */
    public ResultSet executeSQLSelectStatement(String query) {
        ResultSet resultset = null;

        // check if query is set.
        if (query != null && connectionIsOpen()) {
            // if connection open and query is not null execute query
            try {
                resultset = statement.executeQuery(query);
            } catch (SQLException e) {
                resultset = null;
            }
        }

        return resultset;
    }

    /**
     * execute update
     *
     * @param query query
     * @return result
     */
    public boolean executeSqlDmlStatement(String query) {
        boolean result = false;

        // check if query is set.
        if (query != null && connectionIsOpen()) {
            // if connection open and query is not null execute query
            try {
                statement.executeUpdate(query);
                result = true;
            } catch (SQLException e) {
                result = false;
            }
        }

        return result;
    }
}
