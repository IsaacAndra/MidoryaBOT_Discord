package com.isaacandra.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseConfigMemberJoinCommand {

    public static void setConfig(Long guildId, Long channelId, Long stickerId, Long autoRole) {
        try (Connection connection = DataBaseManager.connect()) {
            String sql = """
                    INSERT INTO member_join_config (guild_id, channel_id, sticker_id, auto_role) VALUES (?, ?, ?, ?)
                    ON CONFLICT (guild_id) DO UPDATE SET channel_id = EXCLUDED.channel_id, sticker_id = EXCLUDED.sticker_id,
                    auto_role = EXCLUDED.auto_role
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            statement.setLong(1, guildId);
            if (channelId != null) {
                statement.setLong(2, channelId);
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }
            if (stickerId != null) {
                statement.setLong(3, stickerId);
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }
            statement.setLong(4, autoRole);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long[] getConfig(long guildId) {
        long[] config = new long[]{0, 0, 0}; // [channelId, stickerId, autoRole]

        try (Connection connection = DataBaseManager.connect()) {
            String sql = "SELECT channel_id, sticker_id, auto_role FROM member_join_config WHERE guild_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                config[0] = resultSet.getObject("channel_id") != null ? resultSet.getLong("channel_id") : 0;
                config[1] = resultSet.getObject("sticker_id") != null ? resultSet.getLong("sticker_id") : 0;
                config[2] = resultSet.getObject("auto_role") != null ? resultSet.getLong("auto_role") : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return config;
    }
}