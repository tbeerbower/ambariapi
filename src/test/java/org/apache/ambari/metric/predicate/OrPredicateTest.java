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
public class OrPredicateTest {

    @Test
    public void testApply() {
        Resource resource = new ResourceImpl();
        PropertyIdImpl propertyId1 = new PropertyIdImpl("property1", "category1", false);
        PropertyIdImpl propertyId2 = new PropertyIdImpl("property2", "category1", false);
        PropertyIdImpl propertyId3 = new PropertyIdImpl("property3", "category1", false);

        Predicate predicate1 = new EqualsPredicate(propertyId1, "v1");
        Predicate predicate2 = new EqualsPredicate(propertyId2, "v2");
        Predicate predicate3 = new EqualsPredicate(propertyId3, "v3");

        Predicate orPredicate = new OrPredicate(predicate1, predicate2, predicate3);

        resource.setProperty(propertyId1, "big");
        resource.setProperty(propertyId2, "monkey");
        resource.setProperty(propertyId3, "runner");
        Assert.assertFalse(orPredicate.apply(resource));

        resource.setProperty(propertyId2, "v2");
        Assert.assertTrue(orPredicate.apply(resource));
    }

    @Test
    public void testGetProperties() {
        PropertyIdImpl propertyId1 = new PropertyIdImpl("property1", "category1", false);
        PropertyIdImpl propertyId2 = new PropertyIdImpl("property2", "category1", false);
        PropertyIdImpl propertyId3 = new PropertyIdImpl("property3", "category1", false);

        Predicate predicate1 = new EqualsPredicate(propertyId1, "v1");
        Predicate predicate2 = new EqualsPredicate(propertyId2, "v2");
        Predicate predicate3 = new EqualsPredicate(propertyId3, "v3");

        Predicate orPredicate = new OrPredicate(predicate1, predicate2, predicate3);

        Set<PropertyId> ids = orPredicate.getPropertyIds();

        Assert.assertEquals(3, ids.size());
        Assert.assertTrue(ids.contains(propertyId1));
        Assert.assertTrue(ids.contains(propertyId2));
        Assert.assertTrue(ids.contains(propertyId3));
    }

}
