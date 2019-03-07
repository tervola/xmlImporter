package task.reader.database;

import java.sql.SQLException;

/**
 * Created by user on 3/3/2019.
 */
class NotYetImplementedException extends SQLException {
    NotYetImplementedException() {
        super("Not yet implemented!");
    }
}
