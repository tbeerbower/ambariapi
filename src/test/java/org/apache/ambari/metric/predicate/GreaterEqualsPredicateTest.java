package org.apache.ambari.metric.predicate;

import junit.framework.Assert;
import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.internal.ResourceImpl;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;
import org.junit.Test;

import java.util.Set;

/**
 *
 */
public class GreaterEqualsPredicateTest {

    @Test
    public void testApply() {
        Resource resource = new ResourceImpl(Resource.Type.HostComponent);
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        Predicate predicate = new GreaterEqualsPredicate(propertyId, Comparables.forInteger(10));

        resource.setProperty(propertyId, "1");
        Assert.assertFalse(predicate.evaluate(resource));

        resource.setProperty(propertyId, "100");
        Assert.assertTrue(predicate.evaluate(resource));

        resource.setProperty(propertyId, "10");
        Assert.assertTrue(predicate.evaluate(resource));
    }

    @Test
    public void testGetProperties() {
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        GreaterEqualsPredicate predicate = new GreaterEqualsPredicate(propertyId, Comparables.forInteger(10));

        Set<PropertyId> ids = predicate.getPropertyIds();

        Assert.assertEquals(1, ids.size());
        Assert.assertTrue(ids.contains(propertyId));
    }
}
