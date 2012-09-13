package org.apache.ambari.metric.internal;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.Schema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Default schema implementation.
 */
public class SchemaImpl implements Schema {
    private final AbstractResourceProvider resourceProvider;
    private final Map<Resource.Type, PropertyId> keyPropertyIds;

    public SchemaImpl(AbstractResourceProvider resourceProvider, Map<Resource.Type, PropertyId> keyPropertyIds) {
        this.resourceProvider = resourceProvider;
        this.keyPropertyIds   = keyPropertyIds;
    }

    @Override
    public PropertyId getKeyPropertyId(Resource.Type type) {
        return keyPropertyIds.get(type);
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        Set<PropertyId> propertyIds = new HashSet<PropertyId>(resourceProvider.getPropertyIds());
        for (PropertyProvider propertyProvider : resourceProvider.getPropertyProviders()) {
            propertyIds.addAll(propertyProvider.getPropertyIds());
        }
        return propertyIds;
    }

    @Override
    public Map<String, Set<String>> getCategories() {
        Map<String, Set<String>> categories = new HashMap<String, Set<String>>();

        for (PropertyId propertyId : getPropertyIds()) {
            final String category = propertyId.getCategory();
            Set<String> properties = categories.get(category);
            if (properties == null) {
                properties = new HashSet<String>();
                categories.put(category, properties);
            }
            properties.add(propertyId.getName());
        }
        return categories;
    }
}
