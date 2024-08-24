package com.isaacandra.database;

import java.sql.*;

public class DataBaseConfigPrefixCommands {
    private static String defaultPrefix = "!";  // Prefixo padrão


    public static void initializePrefixDb() {
        try (Connection connection = DataBaseManager.connect()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS prefix_config (
                        guild_id BIGINT PRIMARY KEY,
                        prefix VARCHAR(10) NOT NULL
                        );
                    """;

            Statement statement = connection.createStatement();
            statement.execute(sql); // Cria a tabela prefix_config se ela não existir.

            System.out.println("Tabela criada!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
        try (Connection connection = DataBaseManager.connect();
             PreparedStatement selectStatement = connection.prepareStatement("SELECT prefix FROM prefix_config WHERE guild_id = ?")) {

            selectStatement.setLong(1, guildId);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    String prefix = resultSet.getString("prefix");
                    if (prefix != null) {
                        return prefix; // Retorna o prefixo existente
                    } else {
                        // Se o prefixo for nulo, atualiza para o prefixo padrão
                        try (PreparedStatement updateStatement = connection.prepareStatement(
                                "UPDATE prefix_config SET prefix = ? WHERE guild_id = ?")) {
                            updateStatement.setString(1, defaultPrefix);
                            updateStatement.setLong(2, guildId);
                            updateStatement.executeUpdate();
                        }
                        System.out.println("Prefixo estava nulo, setando para o padrão: " + defaultPrefix);
                        return defaultPrefix;
                    }
                } else {
                    // Caso não exista um registro para esse guildId, insere um novo com o prefixo padrão
                    try (PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO prefix_config (guild_id, prefix) VALUES (?, ?)")) {
                        insertStatement.setLong(1, guildId);
                        insertStatement.setString(2, defaultPrefix);
                        insertStatement.executeUpdate();
                    }
                    System.out.println("Registro não encontrado, inserindo o prefixo padrão: " + defaultPrefix);
                    return defaultPrefix;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return defaultPrefix; // Retorna o prefixo padrão em caso de exceção
        }
    }
}
