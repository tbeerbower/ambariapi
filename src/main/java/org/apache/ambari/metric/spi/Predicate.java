package org.apache.ambari.metric.spi;

/**
 * The predicate is used to filter the resources returned from the cluster
 * controller.  The predicate can examine a resource object and determine
 * whether or not it should be included in the returned results.
 */
public interface Predicate {
    /**
     * Evaluate the predicate for the given resource.
     *
     * @param resource  the resource to evaluate the predicate against
     *
     * @return the result of applying the predicate to the given resource
     */
    public boolean evaluate(Resource resource);
}
