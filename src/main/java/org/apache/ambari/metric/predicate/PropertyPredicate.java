package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;

import java.util.Collections;
import java.util.Set;

/**
 *
 */
public abstract class PropertyPredicate implements Predicate {
    private final PropertyId propertyId;

    public PropertyPredicate(PropertyId propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return Collections.singleton(propertyId);
    }

    protected PropertyId getPropertyId() {
        return propertyId;
    }

    @Override
    public String toSQL() throws UnsupportedOperationException {
        return (propertyId.getCategory() == null ? "" : propertyId.getCategory() + ".") + propertyId.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyPredicate)) return false;

        PropertyPredicate that = (PropertyPredicate) o;

        if (propertyId != null ? !propertyId.equals(that.propertyId) : that.propertyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return propertyId != null ? propertyId.hashCode() : 0;
    }
}

