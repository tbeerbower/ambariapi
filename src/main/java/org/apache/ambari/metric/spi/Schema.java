package org.apache.ambari.metric.spi;

import java.util.Map;
import java.util.Set;

/**
 * The schema is used to describe all of the properties that a provider
 * supports.
 */
public interface Schema {

    /**
     * Get the property id for the property that uniquely identifies
     * the given resource type for the resource described by this schema.
     * </p>
     * For example, the resource 'HostComponent' is uniquely identified by
     * its associated 'Cluster', 'Host' and 'Component' resources.  Passing
     * the 'Host' resource type to
     * {@link Schema#getKeyPropertyId(org.apache.ambari.metric.spi.Resource.Type)}
     * on a schema object of a 'HostComponent' resource will return the id of the
     * property of the foreign key reference from the 'HostComponent' to the 'Host'.
     *
     * @param type  the resource type
     *
     * @return the key property id for the given resource type
     */
    public PropertyId getKeyPropertyId(Resource.Type type);

    /**
     * Get the map of categories for this schema's resource.  The map
     * is keyed by the category name and contains sets of property ids
     * for each category.
     *
     * @return the map of categories
     */
    public Map<String, Set<String>> getCategories();
}
