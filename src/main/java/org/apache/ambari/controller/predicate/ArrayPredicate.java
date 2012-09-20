package org.apache.ambari.controller.predicate;

import org.apache.ambari.controller.spi.PropertyId;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Predicate which evaluates an array of predicates.
 */
public abstract class ArrayPredicate implements BasePredicate {
    private final BasePredicate[] predicates;
    private final Set<PropertyId> propertyIds = new HashSet<PropertyId>();

    public ArrayPredicate(BasePredicate ... predicates) {
        this.predicates = predicates;
        for (BasePredicate predicate : predicates) {
            propertyIds.addAll(predicate.getPropertyIds());
        }
    }

    public BasePredicate[] getPredicates() {
        return predicates;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return propertyIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayPredicate)) return false;

        ArrayPredicate that = (ArrayPredicate) o;

        if (propertyIds != null ? !propertyIds.equals(that.propertyIds) : that.propertyIds != null) return false;

        // don't care about array order
        List<BasePredicate> listPredicates = Arrays.asList(predicates);
        if (listPredicates.size() != that.predicates.length) return false;
        for (BasePredicate predicate : predicates) {
            if (!listPredicates.contains(predicate)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = predicates != null ? Arrays.hashCode(predicates) : 0;
        result = 31 * result + (propertyIds != null ? propertyIds.hashCode() : 0);
        return result;
    }

    @Override
    public void accept(PredicateVisitor visitor) {
        visitor.acceptArrayPredicate(this);
    }

    public abstract String getOperator();
}
