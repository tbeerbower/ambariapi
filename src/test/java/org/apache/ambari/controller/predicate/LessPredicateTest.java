package org.apache.ambari.controller.predicate;

import junit.framework.Assert;
import org.apache.ambari.controller.internal.PropertyIdImpl;
import org.apache.ambari.controller.internal.ResourceImpl;
import org.apache.ambari.controller.spi.Predicate;
import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Resource;
import org.junit.Test;

import java.util.Set;

/**
 *
 */
public class LessPredicateTest {

    @Test
    public void testApply() {
        Resource resource = new ResourceImpl(Resource.Type.HostComponent);
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        Predicate predicate = new LessPredicate(propertyId, Comparables.forInteger(10));

        resource.setProperty(propertyId, "1");
        Assert.assertTrue(predicate.evaluate(resource));

        resource.setProperty(propertyId, "100");
        Assert.assertFalse(predicate.evaluate(resource));

        resource.setProperty(propertyId, "10");
        Assert.assertFalse(predicate.evaluate(resource));
    }

    @Test
    public void testGetProperties() {
        PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
        LessPredicate predicate = new LessPredicate(propertyId, Comparables.forInteger(10));

        Set<PropertyId> ids = predicate.getPropertyIds();

        Assert.assertEquals(1, ids.size());
        Assert.assertTrue(ids.contains(propertyId));
    }
}
