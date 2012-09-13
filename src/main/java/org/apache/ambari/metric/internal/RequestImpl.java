package org.apache.ambari.metric.internal;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Request;

import java.util.HashSet;
import java.util.Set;

/**
 * Default request implementation.
 */
public class RequestImpl implements Request {

    private final Set<PropertyId> propertyIds;

    public RequestImpl(Set<PropertyId> propertyIds) {
        this.propertyIds = propertyIds;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return new HashSet<PropertyId>(propertyIds);
    }

    @Override
    public TemporalInfo getTemporalInfo(PropertyId id) {
        return null;  //TODO
    }
}
