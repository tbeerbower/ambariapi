package org.apache.ambari.api.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public interface ConnectionFactory {
    public Connection getConnection() throws SQLException;
}
