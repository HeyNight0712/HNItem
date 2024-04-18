package com.heynight0712.hnitem.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private final Connection connection;

    public DatabaseManager(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
    }

    /**
     * 關閉數據庫
     * @throws SQLException SQLException
     */
    public void closeConnection() throws SQLException {
        if (connection != null && connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
