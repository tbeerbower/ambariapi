package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

import java.util.Comparator;

/**
 *
 */
public class EqualsPredicate extends ComparisonPredicate {

    public EqualsPredicate(PropertyId propertyId, String value) {
        super(propertyId, value, null);
    }

    public EqualsPredicate(PropertyId propertyId, String value, Comparator<String> comparator) {
        super(propertyId, value, comparator);
    }

    @Override
    public boolean apply(Resource resource) {
        Comparator<String> comparator = getComparator();
        if (comparator == null) {
            return getValue().equals(resource.getPropertyValue(getPropertyId()));
        }
        return comparator.compare(getValue(), resource.getPropertyValue(getPropertyId()))== 0;
    }

    @Override
    public String toSQL() throws UnsupportedOperationException {
        return toSQL("=");
    }
}
