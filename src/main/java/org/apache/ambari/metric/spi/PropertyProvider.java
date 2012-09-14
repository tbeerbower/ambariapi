package org.apache.ambari.metric.spi;

import java.util.Set;

/**
 * The property provider allows us to plug in various property sources into a
 * resource provider.  The property provider is able to populate, or partially
 * populate a resource object with property values.  The resource provider
 * drives this by calling populateResource on the property provider with a list
 * of desired property ids.
 */
public interface PropertyProvider {

    public void populateResource(Resource resource, Request request, Predicate predicate);

    public Set<PropertyId> getPropertyIds();
}
