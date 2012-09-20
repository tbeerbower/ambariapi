package org.apache.ambari.controller.internal;

import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Request;

import java.util.Collections;
import java.util.Set;

/**
 * Default request implementation.
 */
public class RequestImpl implements Request {

    private final Set<PropertyId> propertyIds;

    public RequestImpl(Set<PropertyId> propertyIds) {
        this.propertyIds = Collections.unmodifiableSet(propertyIds);
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return propertyIds;
    }

    @Override
    public TemporalInfo getTemporalInfo(PropertyId id) {
        return null;  //TODO
    }
}
