package org.apache.ambari.controller.predicate;

import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Resource;

/**
 * Predicate that checks if a given value is greater than a {@link Resource} property.
 */
public class GreaterPredicate extends ComparisonPredicate{

    public GreaterPredicate(PropertyId propertyId, Comparable<String> value) {
        super(propertyId, value);
    }

    @Override
    public boolean evaluate(Resource resource) {
        return getValue().compareTo(resource.getPropertyValue(getPropertyId()))< 0;
    }

    @Override
    public String getOperator() {
        return ">";
    }
}
