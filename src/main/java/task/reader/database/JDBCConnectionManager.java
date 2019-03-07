package task.reader.database;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Created by user on 2/23/2019.
 */
public class JDBCConnectionManager {

    final private Connection connection;

    public JDBCConnectionManager(final File config) throws IOException, SQLException, CustomSQLException {
        final ConfigReader configReader = new ConfigReader(config);

        createDataBaseIfDoesNotExist(configReader);
        connection = DriverManager.getConnection(configReader.getConnectionString() + configReader.getDatabaseName(),
                configReader.getUserName(),
                configReader.getPassword());
        createTableIfDoesNotExist();

    }

    private void createTableIfDoesNotExist() throws CustomSQLException {
        createTableClients();
        createTableTransactions();
    }

    private void createTableTransactions() throws CustomSQLException {
        String sql;
        sql = String.format("CREATE TABLE IF NOT EXISTS %s(" +
                "transaction_id SERIAL NOT NULL," +
                "place VARCHAR(100) NOT NULL," +
                "amount NUMERIC(100,2) NOT NULL," +
                "currency varchar(4) NOT NULL," +
                "card VARCHAR(16) NOT NULL, " +
                "client_inn VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (transaction_id)," +
                "CONSTRAINT fk_client_inn FOREIGN KEY (client_inn) REFERENCES %s (inn))", Table.TABLE_NAME_TRANSACTIONS.getValue(), Table.TABLE_NAME_CLIENTS.getValue());
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomSQLException("Error, while creating table: " + Table.TABLE_NAME_TRANSACTIONS.getValue(), e);
        }
    }

    private void createTableClients() throws CustomSQLException {
        final String sql = String.format("CREATE TABLE IF NOT EXISTS %s(" +
                "first_name VARCHAR(100) NOT NULL," +
                "second_name VARCHAR(100) NOT NULL," +
                "middle_name VARCHAR(100) NOT NULL," +
                "inn VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (inn)," +
                "UNIQUE (inn))", Table.TABLE_NAME_CLIENTS.getValue());

        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomSQLException("Error, while creating table: " + Table.TABLE_NAME_CLIENTS.getValue(), e);
        }
    }

    private void createDataBaseIfDoesNotExist(final ConfigReader configReader) throws SQLException {
        String sql;
        try (final Connection serverConnection = DriverManager.getConnection(configReader.getConnectionString(),
                configReader.getUserName(), configReader.getPassword())) {
            sql = "SELECT datname FROM pg_database WHERE datistemplate=false;";

            boolean dbFound = false;
            try (final PreparedStatement preparedStatement = serverConnection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(configReader.getDatabaseName())) {
                        dbFound = true;
                        break;
                    }
                }
            }

            if (!dbFound) {
                sql = String.format("CREATE DATABASE %s", configReader.getDatabaseName());
                try (PreparedStatement preparedStatement = serverConnection.prepareStatement(sql)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
