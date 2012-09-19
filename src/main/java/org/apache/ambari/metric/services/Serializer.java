package org.apache.ambari.metric.services;

/**
 * Serialize the result according to the request.
 */
public interface Serializer {
    /**
     * Serialize the given object returning the serialized form.
     *
     * @param result  the object to serialize
     * @return the serialized form of the result object
     */
    public Object serialize(Object result);
}
