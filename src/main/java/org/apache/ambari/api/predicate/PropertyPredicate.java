package org.apache.ambari.api.predicate;

import org.apache.ambari.api.spi.PropertyId;

import java.util.Collections;
import java.util.Set;

/**
 * Predicate that is associated with a resource property.
 */
public abstract class PropertyPredicate implements BasePredicate {
    private final PropertyId propertyId;

    public PropertyPredicate(PropertyId propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return Collections.singleton(propertyId);
    }

    public PropertyId getPropertyId() {
        return propertyId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof PropertyPredicate)) {
            return false;
        }

        PropertyPredicate that = (PropertyPredicate) o;

        return propertyId == null ? that.propertyId == null : propertyId.equals(that.propertyId);
    }

    @Override
    public int hashCode() {
        return propertyId != null ? propertyId.hashCode() : 0;
    }
}

