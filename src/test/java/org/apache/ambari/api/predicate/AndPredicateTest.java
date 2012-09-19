package org.apache.ambari.api.predicate;

import junit.framework.Assert;
import org.apache.ambari.api.internal.ResourceImpl;
import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.internal.PropertyIdImpl;
import org.apache.ambari.api.spi.Resource;
import org.junit.Test;

import java.util.Set;

/**
 *
 */
public class AndPredicateTest {

    @Test
    public void testApply() {
        Resource resource = new ResourceImpl(Resource.Type.HostComponent);
        PropertyIdImpl propertyId1 = new PropertyIdImpl("property1", "category1", false);
        PropertyIdImpl propertyId2 = new PropertyIdImpl("property2", "category1", false);
        PropertyIdImpl propertyId3 = new PropertyIdImpl("property3", "category1", false);

        EqualsPredicate predicate1 = new EqualsPredicate(propertyId1, "v1");
        EqualsPredicate predicate2 = new EqualsPredicate(propertyId2, "v2");
        EqualsPredicate predicate3 = new EqualsPredicate(propertyId3, "v3");

        AndPredicate andPredicate = new AndPredicate(predicate1, predicate2, predicate3);

        resource.setProperty(propertyId1, "v1");
        resource.setProperty(propertyId2, "monkey");
        resource.setProperty(propertyId3, "v3");
        Assert.assertFalse(andPredicate.evaluate(resource));

        resource.setProperty(propertyId2, "v2");
        Assert.assertTrue(andPredicate.evaluate(resource));
    }

    @Test
    public void testGetProperties() {
        PropertyIdImpl propertyId1 = new PropertyIdImpl("property1", "category1", false);
        PropertyIdImpl propertyId2 = new PropertyIdImpl("property2", "category1", false);
        PropertyIdImpl propertyId3 = new PropertyIdImpl("property3", "category1", false);

        EqualsPredicate predicate1 = new EqualsPredicate(propertyId1, "v1");
        EqualsPredicate predicate2 = new EqualsPredicate(propertyId2, "v2");
        EqualsPredicate predicate3 = new EqualsPredicate(propertyId3, "v3");

        AndPredicate andPredicate = new AndPredicate(predicate1, predicate2, predicate3);

        Set<PropertyId> ids = andPredicate.getPropertyIds();

        Assert.assertEquals(3, ids.size());
        Assert.assertTrue(ids.contains(propertyId1));
        Assert.assertTrue(ids.contains(propertyId2));
        Assert.assertTrue(ids.contains(propertyId3));
    }

}
