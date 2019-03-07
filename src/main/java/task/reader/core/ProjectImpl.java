package task.reader.core;

import task.reader.core.dto.TransactionElement;
import task.reader.database.CustomSQLException;
import task.reader.database.JDBCConnectionManager;
import task.reader.database.JDBCDatabaseManagerImpl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProjectImpl {
    public static void main(String[] args) {
        launch();
    }

    public static void launch(final File config){
        try {
            final JDBCConnectionManager connectionManager = new JDBCConnectionManager(config);
            final JDBCDatabaseManagerImpl databaseManager = new JDBCDatabaseManagerImpl(connectionManager.getConnection());
            final List<RecordView> records = FileChooserImpl.openWindow();
            if (records == null) {
                System.exit(0);
            }

            for (RecordView record : records) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<TransactionElement> transactionElements = JacksonXmlHelper.fromXml(new File(record.getFilePath()));
                        for (final TransactionElement element : transactionElements) {
                            try {
                                databaseManager.importTransaction(element);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (final IOException | SQLException | CustomSQLException e) {
            e.printStackTrace();
        }
    }

    public static void launch(){
        launch(null);
    }
}
