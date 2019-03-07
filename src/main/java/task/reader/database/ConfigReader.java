package task.reader.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by user on 2/23/2019.
 */
public class ConfigReader {

    private final static String DB_PROPERTY_FILE = "config.properties";
    private final String serverName;
    private final String dbName;
    private final String port;
    private final String driver;
    private final String userName;
    private final String password;
    private final String tableName;

    ConfigReader(final File config) throws IOException {
        final Properties properties = new Properties();

        if (config == null) {
            ClassLoader classLoader = this.getClass().getClassLoader();

            try (InputStream inputStream = classLoader.getResourceAsStream(DB_PROPERTY_FILE)) {
                properties.load(inputStream);
            }
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(config)) {
                properties.load(fileInputStream);
            }
        }

        serverName = properties.getProperty("server.database.host");
        dbName = properties.getProperty("server.database.name");
        port = properties.getProperty("server.port");
        driver = properties.getProperty("server.driver");
        userName = properties.getProperty("server.database.user.name");
        password = properties.getProperty("server.database.user.password");
        tableName = properties.getProperty("server.database.table");
    }

    String getConnectionString() {
        return String.format("%s%s:%s/", driver, serverName, port);
    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }

    String getDatabaseName() {
        return dbName;
    }

    public String getTableName() {
        return tableName;
    }
}

