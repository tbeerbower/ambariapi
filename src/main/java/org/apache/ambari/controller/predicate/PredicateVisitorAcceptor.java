package org.apache.ambari.controller.predicate;

/**
 * An acceptor of predicate visitors.
 */
public interface PredicateVisitorAcceptor {

    public void accept(PredicateVisitor visitor);
}
