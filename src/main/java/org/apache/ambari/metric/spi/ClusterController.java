package org.apache.ambari.metric.spi;

/**
 * The cluster controller is the main access point for getting properties
 * from the backend providers.  A cluster controller maintains a mapping of
 * resource providers keyed by resource types.
 */
public interface ClusterController {

    // ----- Monitoring ------------------------------------------------------

    /**
     * Get the resources of the given type filtered by the given request and
     * predicate objects.
     *
     * @param type       the type of the requested resources
     * @param request    the request object which defines the desired set of properties
     * @param predicate  the predicate object which filters which resources are returned
     *
     * @return an iterable object of the requested resources
     */
    public Iterable<Resource> getResources(Resource.Type type, Request request, Predicate predicate);

    /**
     * Get the {@link Schema schema} for the given resource type.  The schema
     * for a given resource type describes the properties and categories provided
     * by that type of resource.
     *
     * @param type  the resource type
     *
     * @return the schema object for the given resource
     */
    public Schema getSchema(Resource.Type type);


    // ----- Management -------------------------------------------------------
    // TODO
}
