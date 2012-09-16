package org.apache.ambari.metric.predicate;

import junit.framework.Assert;
import org.apache.ambari.metric.internal.ResourceImpl;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.spi.Resource;
import org.junit.Test;

import java.util.Set;

/**
 *
 */
public class EqualsPredicateTest {

    @Test
    public void testApply() {
        Resource resource = new ResourceImpl(Resource.Type.HostComponent);
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        Predicate predicate = new EqualsPredicate(propertyId, "bar");

        resource.setProperty(propertyId, "monkey");
        Assert.assertFalse(predicate.evaluate(resource));

        resource.setProperty(propertyId, "bar");
        Assert.assertTrue(predicate.evaluate(resource));
    }

    @Test
    public void testGetProperties() {
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        EqualsPredicate predicate = new EqualsPredicate(propertyId, "bar");

        Set<PropertyId> ids = predicate.getPropertyIds();

        Assert.assertEquals(1, ids.size());
        Assert.assertTrue(ids.contains(propertyId));
    }



}
