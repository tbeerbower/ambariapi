package org.apache.ambari.metric.jdbc;

import org.apache.ambari.metric.internal.AbstractResourceProvider;
import org.apache.ambari.metric.internal.ResourceImpl;
import org.apache.ambari.metric.internal.SchemaImpl;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.Request;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.Schema;

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
public abstract class JDBCResourceProvider extends AbstractResourceProvider {

    private static final Set<PropertyId> RESOURCE_PROPERTY_IDS = new HashSet<PropertyId>();

    static {
        RESOURCE_PROPERTY_IDS.add(new PropertyIdImpl("role_id", "HostRoles", false));
        RESOURCE_PROPERTY_IDS.add(new PropertyIdImpl("cluster_name", "HostRoles", false));
        RESOURCE_PROPERTY_IDS.add(new PropertyIdImpl("host_name", "HostRoles", false));
        RESOURCE_PROPERTY_IDS.add(new PropertyIdImpl("component_name", "HostRoles", false));
        RESOURCE_PROPERTY_IDS.add(new PropertyIdImpl("state", "HostRoles", false));
    }

    private static final Map<Resource.Type, PropertyId> KEY_PROPERTY_IDS = new HashMap<Resource.Type, PropertyId>();

    static {
        KEY_PROPERTY_IDS.put(Resource.Type.Cluster,   new PropertyIdImpl("cluster_name",   "HostRoles", false));
        KEY_PROPERTY_IDS.put(Resource.Type.Host,      new PropertyIdImpl("host_name",      "HostRoles", false));
        KEY_PROPERTY_IDS.put(Resource.Type.Component, new PropertyIdImpl("component_name", "HostRoles", false));
    }


    private final Schema schema;

    protected JDBCResourceProvider() {
        schema = new SchemaImpl(this, KEY_PROPERTY_IDS);
    }

    @Override
    public Set<Resource> getResources(Request request, Predicate predicate) {

        Set<Resource> resources = new HashSet<Resource>();
        Set<PropertyId> propertyIds = request.getPropertyIds();
        if ( propertyIds == null || propertyIds.isEmpty() ) {
            propertyIds = RESOURCE_PROPERTY_IDS;
        } else {
            if (predicate != null) {
                propertyIds.addAll(predicate.getPropertyIds());
            }
            propertyIds.retainAll(RESOURCE_PROPERTY_IDS);
        }

        try {
            ResultSet rs;

            Connection connection = getConnection();

            try {
                String sql = getSQL(propertyIds, predicate);

                System.out.println(sql);

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                rs =  statement.executeQuery(sql);

                while (rs.next()) {
                    ResultSetMetaData metaData    = rs.getMetaData();
                    int               columnCount = metaData.getColumnCount();

                    final ResourceImpl resource = new ResourceImpl();
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
            e.printStackTrace();
        }

        propertyIds = request.getPropertyIds();
        if ( propertyIds == null || propertyIds.isEmpty() ) {
            propertyIds = schema.getPropertyIds();
        } else {
            if (predicate != null) {
                propertyIds.addAll(predicate.getPropertyIds());
            }
            propertyIds.retainAll(schema.getPropertyIds());
        }

        for (Resource resource : resources) {
            for (PropertyProvider propertyProvider : getPropertyProviders()) {
                propertyProvider.populateResource(resource, new HashSet<PropertyId>(propertyIds));
            }
        }

        return resources;
    }

    protected abstract Connection getConnection() throws SQLException;

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

        if (propertyIds.containsAll(predicate.getPropertyIds())) {
            String whereClause = null;
            try {
                whereClause = predicate.toSQL();
            } catch (UnsupportedOperationException e) {
                // Do nothing ... just get all the rows
            }
            sql = sql + " where " + whereClause;
        }

        System.out.println(sql);

        return sql;
    }

    @Override
    public void populateResource(Resource resource, Set<PropertyId> ids) {

    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return RESOURCE_PROPERTY_IDS;
    }

    @Override
    public Schema getSchema() {
        return schema;
    }
}
