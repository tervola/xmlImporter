package task.reader.database;

import task.reader.core.dto.Client;
import task.reader.core.dto.TransactionElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 3/3/2019.
 */
public class JDBCDatabaseManagerImpl implements DatabaseManager {

    private Connection connection;

    public JDBCDatabaseManagerImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public synchronized void importTransaction(final TransactionElement element) throws SQLException {
        final Client client = element.getClient();

        final String insertTransaction = String.format("INSERT INTO %s"
                + "(transaction_id, place, amount, currency, card, client_inn)" +
                "    VALUES (DEFAULT, ?, ?, ?, ?, ?)", Table.TABLE_NAME_TRANSACTIONS.getValue());

        final String insertClient = String.format("INSERT INTO %s"
                + "(first_name, second_name, middle_name, inn)" +
                "    VALUES (?, ?, ?, ?)", Table.TABLE_NAME_CLIENTS.getValue());

        final String checkClient = String.format("select %s FROM %s WHERE %1$s='%s'",
                Table.TABLE_CLIENTS_COLUMN_INN.getValue(), Table.TABLE_NAME_CLIENTS.getValue(), client.getInn());

        try (PreparedStatement transactionPrepared = connection.prepareStatement(insertTransaction)) {
            transactionPrepared.setString(1, element.getPlace());
            transactionPrepared.setDouble(2, element.getAmount());
            transactionPrepared.setString(3, element.getCurrency());
            transactionPrepared.setString(4, element.getCard());
            transactionPrepared.setString(5, client.getInn());

            final boolean clientExist;
            try (PreparedStatement checkPrepared = connection.prepareStatement(checkClient)) {
                clientExist = checkPrepared.execute();
            }

            connection.setAutoCommit(false);
            if (!clientExist) {
                try (PreparedStatement clientPrepared = connection.prepareStatement(insertClient)) {
                    clientPrepared.setString(1, client.getFirstName());
                    clientPrepared.setString(2, client.getLastName());
                    clientPrepared.setString(3, client.getMiddleName());
                    clientPrepared.setString(4, client.getInn());

                    clientPrepared.executeUpdate();
                }
            }

            transactionPrepared.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<TransactionElement> getAllTransactions() throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public TransactionElement getTransactionByClient(final String inn) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<TransactionElement> getTransactionByTransactionProperty(final String property) throws SQLException {
        throw new NotYetImplementedException();
    }

}
