package org.apache.ambari.metric.services.formatters;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/17/12
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFormatter implements ResultFormatter {
    Object serialize(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true);
        try {

            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            //todo
            throw new RuntimeException("Unable to serialize json: " + e, e);
        }
    }
}
