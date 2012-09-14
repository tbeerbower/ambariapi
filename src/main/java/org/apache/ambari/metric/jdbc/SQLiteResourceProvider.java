package org.apache.ambari.metric.jdbc;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class SQLiteResourceProvider extends JDBCResourceProvider {
    private final String connectionURL;

    public SQLiteResourceProvider(String connectionURL, Resource.Type type, Set<PropertyId> propertyIds, Map<String, PropertyId> keyPropertyIds) {
        super(type, propertyIds, keyPropertyIds);
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
