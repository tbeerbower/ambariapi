package org.apache.ambari.metric.utilities;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public class PropertiesTest {

    @Test
    public void testGetPropertyIds() throws Exception {


        Set<PropertyId> propertyIds = Properties.getPropertyIds(Resource.Type.HostComponent, "DB");

        for (PropertyId propertyId : propertyIds) {
//            System.out.println(propertyId);
        }

        propertyIds = Properties.getPropertyIds(Resource.Type.HostComponent, "JMX");

        for (PropertyId propertyId : propertyIds) {
//            System.out.println(propertyId);
        }

        propertyIds = Properties.getPropertyIds(Resource.Type.HostComponent, "GANGLIA");

        for (PropertyId propertyId : propertyIds) {
//            System.out.println(propertyId);
        }
    }


    @Test
    public void testGetKeyPropertyIds() throws Exception {

        Map<String, PropertyId> keyProperties =  Properties.getKeyPropertyIds(Resource.Type.HostComponent);

//        System.out.println(keyProperties);
    }



}
