package com.heynight0712.hnitem.data.database;

import com.heynight0712.hnitem.HNItem;
import com.heynight0712.hnitem.data.MapInfo;

import java.sql.*;
import java.time.LocalDate;

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
                Locked BOOLEAN NOT NULL,
                DateTime TEXT DEFAULT (date('now', 'localtime')),
                Lore TEXT);
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            HNItem.getInstance().getLogger().info("Map Table created successfully or already exists.");
        } catch (SQLException e) {
            HNItem.getInstance().getLogger().severe("Error creating the table: " + e.getMessage());
        }
    }

    public boolean addMap(int mpaID, String uuid, String name, boolean locked) {
        String sql = "INSERT INTO Map (MAPID, UUID, Name, Locked) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mpaID);
            statement.setString(2, uuid);
            statement.setString(3, name);
            statement.setBoolean(4, locked);

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public MapInfo getMap(int mpaID) {
        String sql = "SELECT * FROM Map WHERE MapID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mpaID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapInfoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    // 從 ResultSet 中解析 MapInfo
    private MapInfo mapInfoFromResultSet(ResultSet resultSet) throws SQLException {
        int mapID = resultSet.getInt("MapID");
        String uuid = resultSet.getString("UUID");
        String name = resultSet.getString("Name");
        boolean locked = resultSet.getBoolean("Locked");
        LocalDate date = LocalDate.parse(resultSet.getString("DateTime"));
        return new MapInfo(mapID, uuid, name, locked, date);
    }
}
