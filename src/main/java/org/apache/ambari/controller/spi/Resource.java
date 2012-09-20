package org.apache.ambari.controller.spi;

import java.util.Map;

/**
 * The resource object represents a requested resource.  The resource
 * contains a collection of values for the requested properties.
 */
public interface Resource {
    /**
     * Get the resource type.
     *
     * @return the resource type
     */
    public Type getType();

    /**
     * Get the map of categories contained by this resource.  The map
     * is keyed by the category name and contains maps of properties
     * for each category.
     *
     * @return the map of categories
     */
    public Map<String, Map<String, String>> getCategories();

    /**
     * Set a string property value for the given property id on this resource.
     *
     * @param id     the property id
     * @param value  the value
     */
    public void setProperty(PropertyId id, String value);

    /**
     * Set a integer property value for the given property id on this resource.
     *
     * @param id     the property id
     * @param value  the value
     */
    public void setProperty(PropertyId id, Integer value);

    /**
     * Set a float property value for the given property id on this resource.
     *
     * @param id     the property id
     * @param value  the value
     */
    public void setProperty(PropertyId id, Float value);

    /**
     * Set a double property value for the given property id on this resource.
     *
     * @param id     the property id
     * @param value  the value
     */
    public void setProperty(PropertyId id, Double value);

    /**
     * Set a long property value for the given property id on this resource.
     *
     * @param id     the property id
     * @param value  the value
     */
    public void setProperty(PropertyId id, Long value);

    /**
     * Get a property value for the given property id from this resource.
     *
     * @param id  the property id
     *
     * @return the property value
     */
    public String getPropertyValue(PropertyId id);

    /**
     * Resource types.
     */
    public enum Type {
        Cluster,
        Service,
        Host,
        Component,
        HostComponent
    }
}
