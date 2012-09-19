package org.apache.ambari.api.predicate;

import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.Resource;

/**
 * Predicate that checks if a given value is less than a {@link Resource} property.
 */
public class LessPredicate extends ComparisonPredicate{

    public LessPredicate(PropertyId propertyId, Comparable<String> value) {
        super(propertyId, value);
    }

    @Override
    public boolean evaluate(Resource resource) {
        return getValue().compareTo(resource.getPropertyValue(getPropertyId()))> 0;
    }

    @Override
    public String getOperator() {
        return "<";
    }
}
