package org.apache.ambari.metric.jmx;

import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.Request;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.utilities.Properties;

import java.util.HashMap;
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

        Set<PropertyId> ids = request.getPropertyIds();
        if ( ids == null || ids.isEmpty() ) {
            ids = getPropertyIds();
        } else {
            if (predicate != null) {
                ids.addAll(predicate.getPropertyIds());
            }
            ids.retainAll(getPropertyIds());
        }

        String hostName = hosts.get(resource.getPropertyValue(new PropertyIdImpl("host_name", "HostRoles", false)));
        String port     = JMX_PORTS.get(resource.getPropertyValue(new PropertyIdImpl("component_name", "HostRoles", false)));

        String jmxSource = hostName + ":" + port;

        if (jmxSource == null) {
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
