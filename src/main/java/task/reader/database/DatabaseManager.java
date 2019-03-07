package task.reader.database;

import task.reader.core.dto.TransactionElement;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 3/3/2019.
 */
public interface DatabaseManager {

    /**
     * insert a new transaction to database into transaction and client tables
     * @param element which will be inserted
     * @throws SQLException
     */
    void importTransaction(TransactionElement element) throws SQLException;

    /**
     *
     * @return List of all transactions existing in the database
     * @throws SQLException
     */
    List<TransactionElement> getAllTransactions() throws SQLException;

    /**
     * Find and return transaction regarding client's INN
     * @param inn
     * @return
     * @throws SQLException
     */
    TransactionElement getTransactionByClient(String inn) throws SQLException;

    /**
     * Find and return transaction regarding transaction property
     * @param property
     * @return
     * @throws SQLException
     */
    List<TransactionElement> getTransactionByTransactionProperty(String property) throws SQLException;


}
