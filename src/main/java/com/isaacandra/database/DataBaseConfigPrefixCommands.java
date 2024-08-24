package com.isaacandra.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseConfigPrefixCommands {

    public static void setPrefix(long guildId, String newPrefix) {

        try (Connection connection = DataBaseManager.connect()) {
            String sql = "INSERT INTO prefix_config (guild_id, prefix) VALUES (?, ?) ON CONFLICT (guild_id) DO UPDATE SET prefix = EXCLUDED.prefix";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            statement.setString(2, newPrefix);
            int rowsAffected = statement.executeUpdate();

            System.out.println("Linhas afetadas na atualização do prefixo: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String getPrefix(long guildId) {
        String defaultPrefix = "!";  // Prefixo padrão
        try (Connection connection = DataBaseManager.connect()) {
            String sql = "SELECT prefix FROM prefix_config WHERE guild_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, guildId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String prefix = resultSet.getString("prefix");
                return prefix;
            } else {
                System.out.println("Prefixo não encontrado, retornando padrão: " + defaultPrefix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultPrefix;
    }
}
