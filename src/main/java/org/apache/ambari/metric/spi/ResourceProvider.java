package org.apache.ambari.metric.spi;

import java.util.Set;

/**
 * The resource provider allows us to plug in back end data stores for a
 * resource type.  The resource provider can be queried for a list of
 * resources of a given type.  The resource provider plugs into and is used
 * by the cluster controller to obtain a list of resources for a given request.
 */
public interface ResourceProvider {
    public Set<Resource> getResources(Request request, Predicate predicate);

    public Set<PropertyId> getPropertyIds();

    public Schema getSchema();
}
