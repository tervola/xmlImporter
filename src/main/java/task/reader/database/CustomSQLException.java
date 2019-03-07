package task.reader.database;

import java.sql.SQLException;

/**
 * Created by user on 2/26/2019.
 */
public class CustomSQLException extends Exception {
    CustomSQLException(final String message, SQLException sqlException) {
        super(message + ", " + sqlException.getMessage());
    }
}
