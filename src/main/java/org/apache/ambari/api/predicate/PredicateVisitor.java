package org.apache.ambari.api.predicate;

/**
 * A visitor of predicates.
 */
public interface PredicateVisitor {

    public void acceptComparisonPredicate(ComparisonPredicate predicate);

    public void acceptArrayPredicate(ArrayPredicate predicate);

    public void acceptUnaryPredicate(UnaryPredicate predicate);
}
