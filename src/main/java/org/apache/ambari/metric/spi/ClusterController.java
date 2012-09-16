package org.apache.ambari.metric.spi;

/**
 * The cluster controller is the main access point for getting properties
 * from the backend providers.  A cluster controller maintains a mapping of
 * resource providers keyed by resource types.
 */
public interface ClusterController {

    // Monitoring ...
    public Iterable<Resource> getResources(Resource.Type type, Request request, Predicate predicate);

    public Schema getSchema(Resource.Type type);

    // Management ...
    // TODO
}
