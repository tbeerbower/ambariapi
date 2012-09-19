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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JDBC based resource provider.
 */
public class JDBCResourceProvider extends AbstractResourceProvider {

    private final Resource.Type type;

    private final Set<PropertyId> propertyIds;

    private final PropertyId[][] f_keys;

    private final Schema schema;

    private final ConnectionFactory connectionFactory;

    //TODO : inject property providers

    private static final Map<Resource.Type, PropertyId[][]> F_KEYS = new HashMap<Resource.Type, PropertyId[][]>();

    static {
        PropertyId[][] f_keys = new PropertyId[][] {
                {new PropertyIdImpl("service_name", "Services", false), new PropertyIdImpl("service_name", "ServiceInfo", false)} };
        F_KEYS.put(Resource.Type.Service, f_keys);

        f_keys = new PropertyId[][] {
                {new PropertyIdImpl("service_name",   "ServiceComponents", false), new PropertyIdImpl("service_name", "ServiceComponentInfo", false)},
                {new PropertyIdImpl("component_name", "ServiceComponents", false), new PropertyIdImpl("component_name", "ServiceComponentInfo", false)} };
        F_KEYS.put(Resource.Type.Component, f_keys);
    }



    private JDBCResourceProvider(ConnectionFactory connectionFactory, Resource.Type type) {
        this.connectionFactory = connectionFactory;
        this.type = type;
        this.propertyIds = Properties.getPropertyIds(type, "DB");
        this.f_keys = F_KEYS.get(type);
        schema = new SchemaImpl(this, Properties.getKeyPropertyIds(type));

//        addPropertyProvider(JMXPropertyProvider.create(type, DBHelper.getHosts()));
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
                String sql = getSQL(propertyIds, predicate, f_keys);

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

    private String getSQL(Set<PropertyId> propertyIds, Predicate predicate, PropertyId[][] f_keys) {

        StringBuilder columns = new StringBuilder();
        Set<String> tableSet = new HashSet<String>();

        for (PropertyId propertyId : propertyIds) {
            if (columns.length() > 0) {
                columns.append(", ");
            }
            columns.append(propertyId.getCategory()).append(".").append(propertyId.getName());
            tableSet.add(propertyId.getCategory());
        }


        boolean haveWhereClause = false;
        StringBuilder whereClause = new StringBuilder();
        if (predicate != null &&
                propertyIds.containsAll(PredicateHelper.getPropertyIds(predicate)) &&
                predicate instanceof PredicateVisitorAcceptor) {

            SQLPredicateVisitor visitor = new SQLPredicateVisitor();
            ((PredicateVisitorAcceptor)predicate).accept(visitor);
            whereClause.append(visitor.getSQL());
            haveWhereClause = true;
        }

        StringBuilder joinClause = new StringBuilder();

        if (f_keys != null) {
            for (PropertyId[] f_key : f_keys) {
                if (haveWhereClause || joinClause.length() > 0) {
                    joinClause.append(" and ");
                }
                String category1 = f_key[0].getCategory();
                joinClause.append(category1).append(".").append(f_key[0].getName());
                joinClause.append("=");
                String category2 = f_key[1].getCategory();
                joinClause.append(category2).append(".").append(f_key[1].getName());
                tableSet.add(category1);
                tableSet.add(category2);
            }
            haveWhereClause = true;
        }

        StringBuilder tables = new StringBuilder();

        for (String table : tableSet) {
            if (tables.length() > 0) {
                tables.append(", ");
            }
            tables.append(table);
        }

        String sql = "select " + columns + " from " + tables;

        if (haveWhereClause) {
            sql = sql + " where " +
                    (whereClause == null ? "" : whereClause) +
                    (joinClause == null ? "" : joinClause);
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
