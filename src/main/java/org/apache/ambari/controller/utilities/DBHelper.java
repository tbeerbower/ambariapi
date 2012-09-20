package org.apache.ambari.controller.utilities;

import org.apache.ambari.controller.jdbc.ConnectionFactory;
import org.apache.ambari.controller.jdbc.SQLiteConnectionFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DBHelper {

    public static final ConnectionFactory CONNECTION_FACTORY = new SQLiteConnectionFactory();

    private static final Map<String, String> HOSTS = readHosts();

    public static Map<String, String> getHosts() {
        return HOSTS;
    }

    private static Map<String, String> readHosts() {
        Map<String, String> hosts = new HashMap<String, String>();

        try {
            Connection connection = CONNECTION_FACTORY.getConnection();

            try {
                String sql = "select attributes from hosts";

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                ResultSet rs =  statement.executeQuery(sql);

                ObjectMapper mapper = new ObjectMapper();

                while (rs.next()) {
                    String attributes = rs.getString(1);

                    if (!attributes.startsWith("[]")) {
                        try {
                            Map<String, String> attributeMap = mapper.readValue(attributes, new TypeReference<Map<String, String>>() { });
                            hosts.put(attributeMap.get("privateFQDN"), attributeMap.get("publicFQDN"));
                        } catch (IOException e) {
                            throw new IllegalStateException("Can't read hosts " + attributes, e);
                        }
                    }
                }

            } finally {
                connection.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Can't access DB.", e);
        }

        System.out.println(hosts);

        return hosts;
    }
}
