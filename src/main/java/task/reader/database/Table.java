package task.reader.database;

/**
 * Created by user on 3/3/2019.
 */
public enum Table {
    TABLE_NAME_TRANSACTIONS("transactions"),
    TABLE_NAME_CLIENTS("clients"),

    TABLE_CLIENTS_COLUMN_INN("inn");

    private String tableName;

    Table(final String tableName) {
        this.tableName = tableName;
    }

    public String getValue() {
        return tableName;
    }
}
