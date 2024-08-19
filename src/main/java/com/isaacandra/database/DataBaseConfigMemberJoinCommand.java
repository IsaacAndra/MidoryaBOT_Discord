package com.isaacandra.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseConfigMemberJoinCommand {

    public static void setConfig(long guildId, long channelId, long stickerId) {
        try (Connection connection = DataBaseManager.connect()) {
            String sql = "INSERT INTO member_join_config (guild_id, channel_id, sticker_id) VALUES (?, ?, ?) " +
                    "ON CONFLICT (guild_id) DO UPDATE SET channel_id = EXCLUDED.channel_id, sticker_id = EXCLUDED.sticker_id";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            statement.setLong(2, channelId);
            statement.setLong(3, stickerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long[] getConfig(long guildId) {
        long[] config = new long[]{0, 0}; // [channelId, stickerId]

        try (Connection connection = DataBaseManager.connect()) {
            String sql = "SELECT channel_id, sticker_id FROM member_join_config WHERE guild_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                config[0] = resultSet.getLong("channel_id");
                config[1] = resultSet.getLong("sticker_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return config;
    }
}