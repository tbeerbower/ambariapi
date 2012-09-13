package org.apache.ambari.metric.spi;

import java.util.Set;

/**
 * The predicate is used to filter the resources returned from the cluster
 * controller.  The predicate can examine a resource object and determine
 * whether or not it should be included in the returned results.
 */
public interface Predicate {
    public boolean apply(Resource resource);

    public Set<PropertyId> getPropertyIds();

    //TODO : added this for optimization... is there a better way?
    public String toSQL() throws UnsupportedOperationException;
}
