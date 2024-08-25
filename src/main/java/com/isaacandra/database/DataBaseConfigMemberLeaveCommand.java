package com.isaacandra.database;

import java.sql.*;

public class DataBaseConfigMemberLeaveCommand {

    public static void initializeMemberLeaveDb() {
        try (Connection connection = DataBaseManager.connect()) {

            String sql = """
                    CREATE TABLE IF NOT EXISTS member_leave_config (
                        guild_id BIGINT PRIMARY KEY NOT NULL,
                        channel_id BIGINT NOT NULL
                    );
                """;

            Statement statement = connection.createStatement();
            statement.execute(sql); // Cria a tabela member_join_config se ela não existir.


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void setConfig(long guildId, long channelId) {

        try (Connection connection = DataBaseManager.connect()) {
            String sql = "INSERT INTO member_leave_config (guild_id, channel_id) VALUES (?, ?) " +
                    "ON CONFLICT (guild_id) DO UPDATE SET channel_id = EXCLUDED.channel_id";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            statement.setLong(2, channelId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long[] getConfig(long guildId) {
        long[] config = new long[]{0}; // [channelId, stickerId]

        try (Connection connection = DataBaseManager.connect()) {
            String sql = "SELECT channel_id FROM member_leave_config WHERE guild_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                config[0] = resultSet.getLong("channel_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return config;
    }
}
