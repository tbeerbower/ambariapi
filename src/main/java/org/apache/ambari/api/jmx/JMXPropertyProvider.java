package org.apache.ambari.api.jmx;

import org.apache.ambari.api.internal.PropertyIdImpl;
import org.apache.ambari.api.spi.Predicate;
import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.PropertyProvider;
import org.apache.ambari.api.spi.Request;
import org.apache.ambari.api.spi.Resource;
import org.apache.ambari.api.utilities.PredicateHelper;
import org.apache.ambari.api.utilities.Properties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Property provider implementation for JMX sources.
 */
public class JMXPropertyProvider implements PropertyProvider {

    /**
     * Map of property ids supported by this provider.
     */
    private final Set<PropertyId> propertyIds;

    private final Map<String, String> hosts;

    private static final Map<String, String> JMX_PORTS = new HashMap<String, String>();

    static {
        JMX_PORTS.put("NAMENODE",     "50070");
        JMX_PORTS.put("HBASE_MASTER", "60010");
        JMX_PORTS.put("JOBTRACKER",   "50030");
        JMX_PORTS.put("DATANODE",     "50075");
        JMX_PORTS.put("TASKTRACKER",  "50060");
    }

    private JMXPropertyProvider(Resource.Type type, Map<String, String> hosts) {
        this.hosts = hosts;
        this.propertyIds = Properties.getPropertyIds(type, "JMX");
    }

    @Override
    public void populateResource(Resource resource, Request request, Predicate predicate) {

        if (getPropertyIds().isEmpty()) {
            return;
        }

        Set<PropertyId> ids = new HashSet<PropertyId>(request.getPropertyIds());
        if ( ids == null || ids.isEmpty() ) {
            ids = getPropertyIds();
        } else {
            if (predicate != null) {
                ids.addAll(PredicateHelper.getPropertyIds(predicate));
            }
            ids.retainAll(getPropertyIds());
        }

        String hostName = hosts.get(resource.getPropertyValue(new PropertyIdImpl("host_name", "HostRoles", false)));
        String port     = JMX_PORTS.get(resource.getPropertyValue(new PropertyIdImpl("component_name", "HostRoles", false)));

        String jmxSource = hostName + ":" + port;

        if (hostName == null || port == null || jmxSource == null) {
            return;
        }

        JMXMetrics metrics = JMXHelper.getJMXMetrics(jmxSource, null);

        for (Map<String, String> propertyMap : metrics.getBeans()) {
            String category = propertyMap.get("tag.context");
            if (category != null) {
                for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                    String name = entry.getKey();

                    PropertyIdImpl propertyId = new PropertyIdImpl(name, category, false);
//                        System.out.println(i + ")" + propertyId);

                    if (ids.contains(propertyId)) {
                        resource.setProperty(propertyId, entry.getValue());
                    }
                }
            }
        }
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return propertyIds;
    }

    /**
     * Factory method.
     *
     * @param type  the {@link Resource.Type resource type}
     *
     * @return a new {@link PropertyProvider} instance
     */
    public static PropertyProvider create(Resource.Type type, Map<String, String> hosts) {
        return new JMXPropertyProvider(type, hosts);
    }
}