package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

/**
 * Predicate that checks if a given value is greater than or equal to a {@link Resource} property.
 */
public class GreaterEqualsPredicate  extends ComparisonPredicate{

    public GreaterEqualsPredicate(PropertyId propertyId, Comparable<String> value) {
        super(propertyId, value);
    }

    @Override
    public boolean evaluate(Resource resource) {
        return getValue().compareTo(resource.getPropertyValue(getPropertyId()))<= 0;
    }

    @Override
    public String getOperator() {
        return ">=";
    }
}
