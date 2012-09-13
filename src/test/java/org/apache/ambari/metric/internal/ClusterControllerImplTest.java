package org.apache.ambari.metric.internal;

import org.apache.ambari.metric.ganglia.GangliaPropertyProvider;
import org.apache.ambari.metric.jdbc.SQLiteResourceProvider;
import org.apache.ambari.metric.jmx.JMXPropertyProvider;
import org.apache.ambari.metric.predicate.AndPredicate;
import org.apache.ambari.metric.predicate.EqualsPredicate;
import org.apache.ambari.metric.predicate.OrPredicate;
import org.apache.ambari.metric.spi.ClusterController;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.Request;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.ResourceProvider;
import org.apache.ambari.metric.internal.ClusterControllerImpl;
import org.apache.ambari.metric.internal.RequestImpl;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ClusterControllerImplTest {

    @Test
    public void testGetResources() throws Exception {

        // create a cluster controller
        ClusterController cc = new ClusterControllerImpl();

        // add a resource provider for host_component resources
        final ResourceProvider resourceProvider = new SQLiteResourceProvider("jdbc:sqlite:src/test/resources/data.db");
        cc.addResourceProvider(Resource.Type.HostComponent, resourceProvider);


        final PropertyProvider pp1 = new JMXPropertyProvider();
        resourceProvider.addPropertyProvider(pp1);

        final PropertyProvider pp2 = new GangliaPropertyProvider();
        resourceProvider.addPropertyProvider(pp2);

        // populate the request object with the categories that we want to get
        Set<PropertyId> propertyIds = new HashSet<PropertyId>();
        propertyIds.add(new PropertyIdImpl("cluster_name"  , "HostRoles", false));
        propertyIds.add(new PropertyIdImpl("host_name"     , "HostRoles", false));
        propertyIds.add(new PropertyIdImpl("component_name", "HostRoles", false));
        propertyIds.add(new PropertyIdImpl("state"         , "HostRoles", false));

        propertyIds.add(new PropertyIdImpl("memNonHeapUsedM"     , "jvm", false));
        propertyIds.add(new PropertyIdImpl("memNonHeapCommittedM", "jvm", false));
        propertyIds.add(new PropertyIdImpl("memHeapUsedM"        , "jvm", false));
        propertyIds.add(new PropertyIdImpl("memHeapCommittedM"   , "jvm", false));

        propertyIds.add(new PropertyIdImpl("cpu_nice" , null, true));
//        propertyIds.add(new PropertyId("disk_free", null, true));
//        propertyIds.add(new PropertyId("mem_free" , null, true));
//        propertyIds.add(new PropertyId("bytes_in" , null, true));

        Request request = new RequestImpl(propertyIds);


        Predicate equalsPredicate1 = new EqualsPredicate(new PropertyIdImpl("cluster_name", "HostRoles", false), "tbmetrictest");
        Predicate equalsPredicate2 = new EqualsPredicate(new PropertyIdImpl("component_name", "HostRoles", false), "DATANODE");
        Predicate equalsPredicate3 = new EqualsPredicate(new PropertyIdImpl("host_name", "HostRoles", false), "ip-10-110-19-142.ec2.internal");
        Predicate equalsPredicate4 = new EqualsPredicate(new PropertyIdImpl("host_name", "HostRoles", false), "domu-12-31-39-16-c1-48.compute-1.internal");
        Predicate orPredicate = new OrPredicate(equalsPredicate3, equalsPredicate4);

//        Predicate equalsPredicate1 = new EqualsPredicate(new PropertyId("component_name", "HostRoles", false), "NAMENODE");
        Predicate andPredicate = new AndPredicate(equalsPredicate1, equalsPredicate2, orPredicate);

        // request the host_component resources; predicate is null so we'll get them all
        Iterable<Resource> i = cc.getResources(Resource.Type.HostComponent, request, andPredicate);

        int cnt = 0;
        for (Resource r : i) {
            System.out.println( r + "\n\n");
            ++cnt;
        }
        System.out.println("Number of resources " + cnt);

        //assert...

    }
}


