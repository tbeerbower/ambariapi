package org.apache.ambari.metric.spi;

import java.util.Map;
import java.util.Set;

/**
 * The schema associates a set of property ids with a provider.
 * The schema is used to describe all of the properties that a provider
 * supports.
 */
public interface Schema {

    public PropertyId getKeyPropertyId(Resource.Type type);

    public Set<PropertyId> getPropertyIds();

    public Map<String, Set<String>> getCategories();

}
