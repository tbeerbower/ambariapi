package org.apache.ambari.api.internal;

import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.PropertyProvider;
import org.apache.ambari.api.spi.Resource;
import org.apache.ambari.api.spi.Schema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Default schema implementation.
 */
public class SchemaImpl implements Schema {
    private final AbstractResourceProvider resourceProvider;
    private final Map<String, PropertyId> keyPropertyIds;

    public SchemaImpl(AbstractResourceProvider resourceProvider, Map<String, PropertyId> keyPropertyIds) {
        this.resourceProvider = resourceProvider;
        this.keyPropertyIds   = keyPropertyIds;
    }

    @Override
    public PropertyId getKeyPropertyId(Resource.Type type) {
        return keyPropertyIds.get(type.toString());
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

    public Set<PropertyId> getPropertyIds() {
        Set<PropertyId> propertyIds = new HashSet<PropertyId>(resourceProvider.getPropertyIds());
        for (PropertyProvider propertyProvider : resourceProvider.getPropertyProviders()) {
            propertyIds.addAll(propertyProvider.getPropertyIds());
        }
        return propertyIds;
    }
}
