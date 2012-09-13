package org.apache.ambari.metric.internal;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Default resource implementation.
 */
public class ResourceImpl implements Resource {
    Map<String, Map<String, String>> categories = new HashMap<String, Map<String, String>>();

    @Override
    public Map<String, Map<String, String>> getCategories() {
        return categories;
    }

    @Override
    public void setProperty(PropertyId id, String value) {
        String category = id.getCategory();

        Map<String, String> properties = categories.get(category);

        if (properties == null) {
            properties = new HashMap<String, String>();
            categories.put(category, properties);
        }

        properties.put(id.getName(), value);
    }

    @Override
    public String getPropertyValue(PropertyId id) {

        Map<String, String> properties = categories.get(id.getCategory());

        if (properties != null) {
            return properties.get(id.getName());
        }

        return null;
    }
}
