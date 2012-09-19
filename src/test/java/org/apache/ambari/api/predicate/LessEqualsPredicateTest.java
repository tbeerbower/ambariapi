package org.apache.ambari.api.predicate;

import junit.framework.Assert;
import org.apache.ambari.api.internal.PropertyIdImpl;
import org.apache.ambari.api.internal.ResourceImpl;
import org.apache.ambari.api.spi.Predicate;
import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.Resource;
import org.junit.Test;

import java.util.Set;

/**
 *
 */
public class LessEqualsPredicateTest {

    @Test
    public void testApply() {
        Resource resource = new ResourceImpl(Resource.Type.HostComponent);
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        Predicate predicate = new LessEqualsPredicate(propertyId, Comparables.forInteger(10));

        resource.setProperty(propertyId, "1");
        Assert.assertTrue(predicate.evaluate(resource));

        resource.setProperty(propertyId, "100");
        Assert.assertFalse(predicate.evaluate(resource));

        resource.setProperty(propertyId, "10");
        Assert.assertTrue(predicate.evaluate(resource));
    }

    @Test
    public void testGetProperties() {
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        LessEqualsPredicate predicate = new LessEqualsPredicate(propertyId, Comparables.forInteger(10));

        Set<PropertyId> ids = predicate.getPropertyIds();

        Assert.assertEquals(1, ids.size());
        Assert.assertTrue(ids.contains(propertyId));
    }
}