package org.apache.ambari.controller.spi;

import java.util.Set;

/**
 * The property provider is used to plug in various property sources into a
 * resource provider.  The property provider is able to populate, or partially
 * populate a given resource object with property values.
 */
public interface PropertyProvider {

    /**
     * Populate the given resource with any properties that this property
     * provider can provide.
     *
     * @param resource   the resource to be populated
     * @param request    the request object which defines the desired set of properties
     * @param predicate  the predicate object which filters which resources are returned
     */
    public void populateResource(Resource resource, Request request, Predicate predicate);

    /**
     * Get the set of property ids for the properties that this provider can provide.
     *
     * @return the set of property ids for the properties that this provider can provide
     */
    public Set<PropertyId> getPropertyIds();
}
