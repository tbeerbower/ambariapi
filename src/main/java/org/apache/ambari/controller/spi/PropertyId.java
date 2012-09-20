package org.apache.ambari.controller.spi;

/**
 * The property id is used to uniquely identify a resource property.  The
 * property id is a composite of property name and category name as
 * well as an indicator for temporal values.
 */
public interface PropertyId {

    /**
     * Get the property name.
     *
     * @return the property name
     */
    public String getName();

    /**
     * Get the category name.
     *
     * @return the category name
     */
    public String getCategory();

    /**
     * Indicates whether or not this property provides a temporal value.
     *
     * @return true if this property provides a temporal value.
     */
    public boolean isTemporal();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object o);
}
