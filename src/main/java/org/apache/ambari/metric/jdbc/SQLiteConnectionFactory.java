package org.apache.ambari.metric.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class SQLiteConnectionFactory implements ConnectionFactory {

    private static String DB_FILE_NAME = System.getProperty("ambariapi.dbfile", "src/test/resources/data.db");

    public static final String CONNECTION_URL = "jdbc:sqlite:" + DB_FILE_NAME;

    public SQLiteConnectionFactory() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Can't load SQLite.", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL);
    }
}
