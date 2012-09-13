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
    private static final Map<String, String> JMX_SOURCES = new HashMap<String, String>();

    static {
        JMX_SOURCES.put("domu-12-31-39-15-25-c7.compute-1.internal:NAMENODE", "ec2-107-22-86-120.compute-1.amazonaws.com:50070");
        JMX_SOURCES.put("domu-12-31-39-15-25-c7.compute-1.internal:HBASE_MASTER", "ec2-107-22-86-120.compute-1.amazonaws.com:60010");
        JMX_SOURCES.put("domu-12-31-39-16-c1-48.compute-1.internal:DATANODE", "ec2-184-73-38-68.compute-1.amazonaws.com:50075");
        JMX_SOURCES.put("domu-12-31-39-16-c1-48.compute-1.internal:JOBTRACKER", "ec2-184-73-38-68.compute-1.amazonaws.com:50030");
        JMX_SOURCES.put("domu-12-31-39-16-c1-48.compute-1.internal:TASKTRACKER", "ec2-184-73-38-68.compute-1.amazonaws.com:50060");
        JMX_SOURCES.put("ip-10-110-19-142.ec2.internal:DATANODE", "ec2-23-22-27-143.compute-1.amazonaws.com:50075");
        JMX_SOURCES.put("ip-10-110-19-142.ec2.internal:TASKTRACKER", "ec2-23-22-27-143.compute-1.amazonaws.com:50060");
        JMX_SOURCES.put("ip-10-111-35-113.ec2.internal:DATANODE", "ec2-107-22-21-111.compute-1.amazonaws.com:50075");
        JMX_SOURCES.put("ip-10-111-35-113.ec2.internal:TASKTRACKER", "ec2-107-22-21-111.compute-1.amazonaws.com:50060");
        JMX_SOURCES.put("ip-10-68-18-171.ec2.internal:DATANODE", "ec2-23-23-56-2.compute-1.amazonaws.com:50075");
        JMX_SOURCES.put("ip-10-68-18-171.ec2.internal:TASKTRACKER", "ec2-23-23-56-2.compute-1.amazonaws.com:50060");
    }

    @Override
    public void populateResource(Resource resource, Set<PropertyId> ids) {
        if ( ids == null || ids.isEmpty() ) {
            ids = getPropertyIds();
        } else {
            ids.retainAll(getPropertyIds());
        }

        String hostComponentName = resource.getPropertyValue(new PropertyIdImpl("host_name", "HostRoles", false)) + ":" +
                                   resource.getPropertyValue(new PropertyIdImpl("component_name", "HostRoles", false));

        String jmxSource = JMX_SOURCES.get(hostComponentName);

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
