package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

import java.util.Comparator;

/**
 *
 */
public class LessEqualsPredicate extends ComparisonPredicate{

    public LessEqualsPredicate(PropertyId propertyId, String value, Comparator<String> comparator) {
        super(propertyId, value, comparator);
    }

    @Override
    public boolean apply(Resource resource) {
        Comparator<String> comparator = getComparator();
        return comparator.compare(resource.getPropertyValue(getPropertyId()), getValue())<= 0;
    }

    @Override
    public String toSQL() throws UnsupportedOperationException {
        return toSQL("<=");
    }
}
