package org.apache.ambari.metric.spi;

import java.util.Map;

/**
 * The resource object is what is returned from the cluster controller as
 * the result of a query.  The resource object is simply a mapping of
 * property ids to property values.  The resource also exposes a type
 * enumeration containing all of the supported resource types.
 */
public interface Resource {
    public Type getType();

    public Map<String, Map<String, String>> getCategories();

    public void setProperty(PropertyId id, String value);

    public void setProperty(PropertyId id, Integer value);

    public void setProperty(PropertyId id, Float value);

    public void setProperty(PropertyId id, Double value);

    public void setProperty(PropertyId id, Long value);

    public String getPropertyValue(PropertyId id);

    public enum Type {
        Cluster,
        Service,
        Host,
        Component,
        HostComponent
    }
}
