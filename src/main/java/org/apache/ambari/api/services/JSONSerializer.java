package org.apache.ambari.api.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;

/**
 * Provides JSON serialization.
 */
public class JSONSerializer implements Serializer {
    /**
     * Serialize the result into JSON representation.
     *
     * @param result  the object to serialize
     * @return the JSON representation of the result
     */
    @Override
    public Object serialize(Object result) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true);
        try {

            return mapper.writeValueAsString(result);
        } catch (IOException e) {
            //todo
            throw new RuntimeException("Unable to serialize json: " + e, e);
        }
    }
}
