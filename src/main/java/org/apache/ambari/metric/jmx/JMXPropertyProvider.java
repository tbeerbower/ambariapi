package org.apache.ambari.metric.jmx;

import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.Resource;

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
    private static final Set<PropertyId> PROPERTY_IDS = new HashSet<PropertyId>();

    static {
        PROPERTY_IDS.add(new PropertyIdImpl("memNonHeapUsedM", "jvm", false));
        PROPERTY_IDS.add(new PropertyIdImpl("memNonHeapCommittedM", "jvm", false));
        PROPERTY_IDS.add(new PropertyIdImpl("memHeapUsedM", "jvm", false));
        PROPERTY_IDS.add(new PropertyIdImpl("memHeapCommittedM", "jvm", false));
    }

    /**
     * Map of jmx sources keyed by internal host name and component type.
     * TODO : how do we get the info for this mapping?
     */
    private static final Map<String, String> HOSTS = new HashMap<String, String>();

    static {
        HOSTS.put("domu-12-31-39-15-25-c7.compute-1.internal", "ec2-107-22-86-120.compute-1.amazonaws.com");
        HOSTS.put("domu-12-31-39-16-c1-48.compute-1.internal", "ec2-184-73-38-68.compute-1.amazonaws.com");
        HOSTS.put("ip-10-110-19-142.ec2.internal", "ec2-23-22-27-143.compute-1.amazonaws.com");
        HOSTS.put("ip-10-111-35-113.ec2.internal", "ec2-107-22-21-111.compute-1.amazonaws.com");
        HOSTS.put("ip-10-68-18-171.ec2.internal", "ec2-23-23-56-2.compute-1.amazonaws.com");
    }

    private static final Map<String, String> JMX_PORTS = new HashMap<String, String>();

    static {
        JMX_PORTS.put("NAMENODE", "50070");
        JMX_PORTS.put("HBASE_MASTER", "60010");
        JMX_PORTS.put("JOBTRACKER", "50030");
        JMX_PORTS.put("DATANODE", "50075");
        JMX_PORTS.put("TASKTRACKER", "50060");
    }


    @Override
    public void populateResource(Resource resource, Set<PropertyId> ids) {
        if ( ids == null || ids.isEmpty() ) {
            ids = getPropertyIds();
        } else {
            ids.retainAll(getPropertyIds());
        }

        String hostName = HOSTS.get(resource.getPropertyValue(new PropertyIdImpl("host_name", "HostRoles", false)));
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
        return PROPERTY_IDS;
    }
}
