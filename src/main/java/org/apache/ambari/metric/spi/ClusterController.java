package org.apache.ambari.metric.spi;

/**
 * The cluster controller is the main access point for getting properties
 * from the backend providers.  A cluster controller maintains a map of
 * resource providers associated with resource types.  The idea is that
 * there will be a singleton cluster controller and we will register one
 * resource provider for each type of resource that we support
 * (Cluster, Service, Component, …).
 */
public interface ClusterController {

    // Monitoring ...
    public Iterable<Resource> getResources(Resource.Type type, Request request, Predicate predicate);

    public void addResourceProvider(Resource.Type type, ResourceProvider provider);

    public Schema getSchema(Resource.Type type);

    // Management ... TBD

}
