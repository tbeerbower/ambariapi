package org.apache.ambari.metric.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class SQLiteResourceProvider extends JDBCResourceProvider {
    private final String connectionURL;

    public SQLiteResourceProvider(String connectionURL) {
        this.connectionURL = connectionURL;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Can't load SQLite.", e);
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL);
    }
}
