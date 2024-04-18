package com.heynight0712.hnitem.data.database;

import com.heynight0712.hnitem.HNItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MapDatabase {
    private final Connection connection;

    public MapDatabase() {
        this.connection = HNItem.getDatabase().getConnection();
    }

    // 在数据库中创建 Map 表
    public void createMapTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Map (
                ID INTEGER PRIMARY KEY,
                UUID TEXT NOT NULL);
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            HNItem.getInstance().getLogger().info("Map Table created successfully or already exists.");
        } catch (SQLException e) {
            HNItem.getInstance().getLogger().severe("Error creating the table: " + e.getMessage());
        }
    }

    public boolean addMap(int id, String uuid) {
        String sql = "INSERT INTO Map (ID, UUID) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, uuid);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 0) {return true;}

            HNItem.getInstance().getLogger().info("[Map Database] " + "ID: " + id + "| UUID: " + uuid + " -- No rows affected.");
            return false;
        } catch (SQLException e) {
            HNItem.getInstance().getLogger().severe("[Map Database] Error adding the map: " + e.getMessage());
            return false;
        }
    }
}
