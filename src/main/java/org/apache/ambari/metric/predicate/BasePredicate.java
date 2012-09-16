package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;

import java.util.Set;

/**
 * An extended predicate interface which allows for the retrieval of any
 * associated property ids.
 */
public interface BasePredicate extends Predicate, PredicateVisitorAcceptor{
    public Set<PropertyId> getPropertyIds();
}
