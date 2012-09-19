package org.apache.ambari.api.predicate;

import org.apache.ambari.api.spi.PropertyId;

import java.util.Set;

/**
 * Predicate that operates on one other predicate.
 */
public abstract class UnaryPredicate implements BasePredicate {
    private final BasePredicate predicate;

    public UnaryPredicate(BasePredicate predicate) {
        this.predicate = predicate;
    }

    public BasePredicate getPredicate() {
        return predicate;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return predicate.getPropertyIds();
    }

    @Override
    public void accept(PredicateVisitor visitor) {
        visitor.acceptUnaryPredicate(this);
    }

    public abstract String getOperator();
}
