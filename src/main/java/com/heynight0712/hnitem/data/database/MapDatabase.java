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
                MapID INTEGER PRIMARY KEY,
                UUID TEXT NOT NULL,
                Name TEXT NOT NULL,
                Locked BOOLEAN NOT NULL);
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            HNItem.getInstance().getLogger().info("Map Table created successfully or already exists.");
        } catch (SQLException e) {
            HNItem.getInstance().getLogger().severe("Error creating the table: " + e.getMessage());
        }
    }

    public boolean addMap(int mpaID, String uuid) {
        String sql = "INSERT INTO Map (MAPID, UUID, Name, Locked) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mpaID);
            statement.setString(2, uuid);
            statement.setString(3, "Unknown");
            statement.setBoolean(4, false);

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
