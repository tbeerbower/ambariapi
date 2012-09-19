package org.apache.ambari.api.utilities;

import org.apache.ambari.api.internal.PropertyIdImpl;
import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Properties {

    private static final String PROPERTIES_FILE = "properties.json";
    private static final String KEY_PROPERTIES_FILE = "key_properties.json";

    private static final Map<String, Map<String, Set<PropertyId>>> PROPERTY_IDS = readPropertyIds(PROPERTIES_FILE);
    private static final Map<String, Map<String, PropertyId>> KEY_PROPERTY_IDS = readKeyPropertyIds(KEY_PROPERTIES_FILE);

    public PropertyId getPropertyId(String name, String category) {
        return new PropertyIdImpl(name, category, false);
    }

    public PropertyId getPropertyId(String name, String category, boolean temporal) {
        return new PropertyIdImpl(name, category, temporal);
    }

    public static Set<PropertyId> getPropertyIds(Resource.Type resourceType, String providerKey) {

        Map<String, Set<PropertyId>> propertyIds = PROPERTY_IDS.get(resourceType.toString());
        if (propertyIds != null) {
            return propertyIds.get(providerKey);
        }
        return Collections.emptySet();
    }

    public static Map<String, PropertyId> getKeyPropertyIds(Resource.Type resourceType) {
        return KEY_PROPERTY_IDS.get(resourceType.toString());
    }

    private static Map<String, Map<String, Set<PropertyId>>> readPropertyIds(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(ClassLoader.getSystemResourceAsStream(filename), new TypeReference<Map<String, Map<String,Set<PropertyIdImpl>>>>() { });
        } catch (IOException e) {
            throw new IllegalStateException("Can't read properties file " + filename, e);
        }
    }

    private static Map<String, Map<String, PropertyId>> readKeyPropertyIds(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(ClassLoader.getSystemResourceAsStream(filename), new TypeReference<Map<String, Map<String, PropertyIdImpl>>>() { });
        } catch (IOException e) {
            throw new IllegalStateException("Can't read properties file " + filename, e);
        }
    }

}
