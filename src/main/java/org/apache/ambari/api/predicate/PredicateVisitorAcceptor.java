package org.apache.ambari.api.predicate;

/**
 * An acceptor of predicate visitors.
 */
public interface PredicateVisitorAcceptor {

    public void accept(PredicateVisitor visitor);
}
