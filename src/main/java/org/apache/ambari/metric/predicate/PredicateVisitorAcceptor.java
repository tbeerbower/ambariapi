package org.apache.ambari.metric.predicate;

/**
 * An acceptor of predicate visitors.
 */
public interface PredicateVisitorAcceptor {

    public void accept(PredicateVisitor visitor);
}
