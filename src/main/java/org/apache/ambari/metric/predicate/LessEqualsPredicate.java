package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;


/**
 * Predicate that checks if a given value is less than or equal to a {@link Resource} property.
 */
public class LessEqualsPredicate extends ComparisonPredicate{

    public LessEqualsPredicate(PropertyId propertyId, Comparable<String> value) {
        super(propertyId, value);
    }

    @Override
    public boolean evaluate(Resource resource) {
        return getValue().compareTo(resource.getPropertyValue(getPropertyId()))>= 0;
    }

    @Override
    public String getOperator() {
        return "<=";
    }
}
