package com.isaacandra.database;

import java.sql.*;

public class DataBaseConfigMemberJoinCommand {

    public static void initializeMemberJoinDb() {
        try (Connection connection = DataBaseManager.connect()) {

            String sql = """
                    CREATE TABLE IF NOT EXISTS member_join_config (
                        guild_id BIGINT PRIMARY KEY NOT NULL,
                        channel_id BIGINT NOT NULL,
                        auto_role TEXT 
                    );
                """;

            Statement statement = connection.createStatement();
            statement.execute(sql); // Cria a tabela member_join_config se ela não existir.


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void setConfig(Long guildId, Long channelId, Long autoRole) {
        try (Connection connection = DataBaseManager.connect()) {
            String sql = """
                    INSERT INTO member_join_config (guild_id, channel_id, auto_role) VALUES (?, ?, ?)
                    ON CONFLICT (guild_id) DO UPDATE SET channel_id = EXCLUDED.channel_id,
                    auto_role = CASE
                        WHEN EXCLUDED.auto_role IS NOT NULL THEN EXCLUDED.auto_role
                        ELSE member_join_config.auto_role
                    END
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            statement.setLong(1, guildId);
            if (channelId != null) {
                statement.setLong(2, channelId);
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }
            if (autoRole != null) {
                statement.setLong(3, autoRole);
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long[] getConfig(long guildId) {
        long[] config = new long[]{0, 0}; // [channelId, autoRole]

        try (Connection connection = DataBaseManager.connect()) {
            String sql = "SELECT channel_id, auto_role FROM member_join_config WHERE guild_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                config[0] = resultSet.getObject("channel_id") != null ? resultSet.getLong("channel_id") : 0;
                config[1] = resultSet.getObject("auto_role") != null ? resultSet.getLong("auto_role") : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return config;
    }
}