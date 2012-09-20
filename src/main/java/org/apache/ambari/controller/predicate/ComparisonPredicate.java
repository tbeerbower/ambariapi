package org.apache.ambari.controller.predicate;

import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Resource;

/**
 * Predicate that compares a given value to a {@link Resource} property.
 */
public abstract class ComparisonPredicate extends PropertyPredicate implements BasePredicate {
    private final Comparable<String> value;

    public ComparisonPredicate(PropertyId propertyId, Comparable<String> value) {
        super(propertyId);
        this.value = value;
    }

    public Comparable<String> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparisonPredicate)) return false;
        if (!super.equals(o)) return false;

        ComparisonPredicate that = (ComparisonPredicate) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public void accept(PredicateVisitor visitor) {
        visitor.acceptComparisonPredicate(this);
    }


    public abstract String getOperator();
}
