package org.apache.ambari.controller.predicate;

import org.apache.ambari.controller.spi.Predicate;
import org.apache.ambari.controller.spi.PropertyId;

import java.util.Set;

/**
 * An extended predicate interface which allows for the retrieval of any
 * associated property ids.
 */
public interface BasePredicate extends Predicate, PredicateVisitorAcceptor{
    public Set<PropertyId> getPropertyIds();
}
