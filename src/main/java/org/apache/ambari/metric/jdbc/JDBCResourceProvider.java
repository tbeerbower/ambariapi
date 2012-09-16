package org.apache.ambari.metric.jdbc;

import org.apache.ambari.metric.internal.AbstractResourceProvider;
import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.internal.ResourceImpl;
import org.apache.ambari.metric.internal.SchemaImpl;
import org.apache.ambari.metric.jmx.JMXPropertyProvider;
import org.apache.ambari.metric.predicate.PredicateVisitorAcceptor;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.Request;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.ResourceProvider;
import org.apache.ambari.metric.spi.Schema;
import org.apache.ambari.metric.utilities.DBHelper;
import org.apache.ambari.metric.utilities.PredicateHelper;
import org.apache.ambari.metric.utilities.Properties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * JDBC based resource provider.
 */
public class JDBCResourceProvider extends AbstractResourceProvider {

    private final Resource.Type type;

    private final Set<PropertyId> propertyIds;

    private final Schema schema;

    private final ConnectionFactory connectionFactory;

    //TODO : inject property providers

    private JDBCResourceProvider(ConnectionFactory connectionFactory, Resource.Type type) {
        this.connectionFactory = connectionFactory;
        this.type = type;
        this.propertyIds = Properties.getPropertyIds(type, "DB");
        schema = new SchemaImpl(this, Properties.getKeyPropertyIds(type));

        addPropertyProvider(JMXPropertyProvider.create(type, DBHelper.getHosts()));
    }

    @Override
    public Set<Resource> getResources(Request request, Predicate predicate) {

        Set<Resource> resources = new HashSet<Resource>();
        Set<PropertyId> propertyIds = new HashSet<PropertyId>(request.getPropertyIds());
        if (propertyIds.isEmpty()) {
            propertyIds.addAll(this.propertyIds);
        } else {
            if (predicate != null) {
                propertyIds.addAll(PredicateHelper.getPropertyIds(predicate));
            }
            propertyIds.retainAll(this.propertyIds);
        }

        try {
            Connection connection = connectionFactory.getConnection();

            try {
                String sql = getSQL(propertyIds, predicate);

//                System.out.println(sql);

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                ResultSet rs =  statement.executeQuery(sql);

                while (rs.next()) {
                    ResultSetMetaData metaData    = rs.getMetaData();
                    int               columnCount = metaData.getColumnCount();

                    final ResourceImpl resource = new ResourceImpl(type);
                    for (int i = 1; i <= columnCount; ++i) {
                        PropertyIdImpl propertyId = new PropertyIdImpl(metaData.getColumnName(i), metaData.getTableName(i), false);

//                        System.out.println(i + ")" + propertyId);

                        if (propertyIds.contains(propertyId)) {
                            resource.setProperty(propertyId, rs.getString(i));
                        }
                    }
                    resources.add(resource);
                }

            } finally {
                connection.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException("DB error : ", e);
        }

        for (Resource resource : resources) {
            for (PropertyProvider propertyProvider : getPropertyProviders()) {
                propertyProvider.populateResource(resource, request, predicate);
            }
        }

        return resources;
    }

    private String getSQL(Set<PropertyId> propertyIds, Predicate predicate) {

        StringBuilder columns = new StringBuilder();
        Set<String> tableSet = new HashSet<String>();

        for (PropertyId propertyId : propertyIds) {
            if (columns.length() > 0) {
                columns.append(", ");
            }
            columns.append(propertyId.getCategory()).append(".").append(propertyId.getName());
            tableSet.add(propertyId.getCategory());
        }
        StringBuilder tables = new StringBuilder();

        for (String table : tableSet) {
            if (tables.length() > 0) {
                tables.append(", ");
            }
            tables.append(table);
        }

        String sql = "select " + columns + " from " + tables;

        if (predicate != null &&
                propertyIds.containsAll(PredicateHelper.getPropertyIds(predicate)) &&
                predicate instanceof PredicateVisitorAcceptor) {
            String whereClause = null;
            try {
                SQLPredicateVisitor visitor = new SQLPredicateVisitor();
                ((PredicateVisitorAcceptor)predicate).accept(visitor);
                whereClause = visitor.getSQL();

            } catch (UnsupportedOperationException e) {
                // Do nothing ... just get all the rows
            }
            sql = sql + " where " + whereClause;
        }

        System.out.println(sql);

        return sql;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return propertyIds;
    }

    @Override
    public Schema getSchema() {
        return schema;
    }

    /**
     * Factory method.
     *
     * @param connectionFactory the factory used to obtain a {@link Connection connection}
     * @param type              the {@link Resource.Type resource type}
     *
     * @return a new {@link ResourceProvider} instance
     */
    public static ResourceProvider create(ConnectionFactory connectionFactory, Resource.Type type) {
        return new JDBCResourceProvider(connectionFactory, type);
    }

}
