package org.apache.ambari.api.predicate;

import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.Resource;

/**
 * Predicate that checks equality of a given value to a {@link Resource} property.
 */
public class EqualsPredicate extends ComparisonPredicate {


    public EqualsPredicate(PropertyId propertyId, Comparable<String> value) {
        super(propertyId, value);
    }

    @Override
    public boolean evaluate(Resource resource) {
        return getValue().compareTo(resource.getPropertyValue(getPropertyId()))== 0;
    }

    @Override
    public String getOperator() {
        return "=";
    }
}
