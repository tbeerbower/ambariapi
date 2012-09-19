package org.apache.ambari.api.predicate;

import org.apache.ambari.api.spi.Predicate;
import org.apache.ambari.api.spi.PropertyId;

import java.util.Set;

/**
 * An extended predicate interface which allows for the retrieval of any
 * associated property ids.
 */
public interface BasePredicate extends Predicate, PredicateVisitorAcceptor{
    public Set<PropertyId> getPropertyIds();
}
