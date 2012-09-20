package org.apache.ambari.controller.internal;

import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Default resource implementation.
 */
public class ResourceImpl implements Resource {
    private final Type type;
    private final Map<String, Map<String, String>> categories = new HashMap<String, Map<String, String>>();

    public ResourceImpl(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

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

    public void setProperty(PropertyId id, Integer value) {
        setProperty(id, value.toString());
    }

    public void setProperty(PropertyId id, Float value) {
        setProperty(id, value.toString());
    }

    public void setProperty(PropertyId id, Double value) {
        setProperty(id, value.toString());
    }

    public void setProperty(PropertyId id, Long value) {
        setProperty(id, value.toString());
    }

    @Override
    public String getPropertyValue(PropertyId id) {

        Map<String, String> properties = categories.get(id.getCategory());

        if (properties != null) {
            return properties.get(id.getName());
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Resource : ").append(type).append("\n");
        for (Map.Entry<String, Map<String, String>> catEntry : categories.entrySet()) {
            for (Map.Entry<String, String> propEntry : catEntry.getValue().entrySet()) {
                sb.append("    ").append(catEntry.getKey()).append(".").append(propEntry.getKey()).append(" : ").append(propEntry.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
}
