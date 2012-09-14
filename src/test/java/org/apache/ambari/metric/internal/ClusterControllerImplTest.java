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
        ClusterController cc = ClusterControllerImpl.getSingleton();


        // populate the request object with the categories that we want to get
        Set<PropertyId> propertyIds = new HashSet<PropertyId>();
//        propertyIds.add(new PropertyIdImpl("cluster_name"  , "HostRoles", false));
//        propertyIds.add(new PropertyIdImpl("host_name"     , "HostRoles", false));
//        propertyIds.add(new PropertyIdImpl("component_name", "HostRoles", false));
//        propertyIds.add(new PropertyIdImpl("state"         , "HostRoles", false));

        Request request = new RequestImpl(propertyIds);

        Predicate equalsPredicate1 = new EqualsPredicate(new PropertyIdImpl("cluster_name", "HostRoles", false), "tbmetrictest");
        Predicate equalsPredicate2 = new EqualsPredicate(new PropertyIdImpl("component_name", "HostRoles", false), "DATANODE");
        Predicate equalsPredicate3 = new EqualsPredicate(new PropertyIdImpl("host_name", "HostRoles", false), "ip-10-110-19-142.ec2.internal");
        Predicate equalsPredicate4 = new EqualsPredicate(new PropertyIdImpl("host_name", "HostRoles", false), "domu-12-31-39-16-c1-48.compute-1.internal");
        Predicate orPredicate = new OrPredicate(equalsPredicate3, equalsPredicate4);

        Predicate andPredicate = new AndPredicate(equalsPredicate1, equalsPredicate2, orPredicate);

        // request the host_component resources
        getResources(Resource.Type.HostComponent, cc, request, andPredicate);

        // request the hosts; predicate is null so we'll get them all
        getResources(Resource.Type.Host, cc, request, null);

        // request the clusters; predicate is null so we'll get them all
        getResources(Resource.Type.Cluster, cc, request, null);

        // request the services; predicate is null so we'll get them all
        getResources(Resource.Type.Service, cc, request, null);


        PropertyId serviceIdProperty = cc.getSchema(
                Resource.Type.Service).getKeyPropertyId(Resource.Type.Service);


        // request the components; predicate is null so we'll get them all
        getResources(Resource.Type.Component, cc, request, null);

    }

    private void getResources(Resource.Type type, ClusterController cc, Request request, Predicate predicate) {
        Iterable<Resource> i;
        int cnt;
        i = cc.getResources(type, request, predicate);

        cnt = 0;
        for (Resource r : i) {
            System.out.println( r + "\n\n");
            ++cnt;
        }
        System.out.println("Number of resources " + cnt);
    }
}

