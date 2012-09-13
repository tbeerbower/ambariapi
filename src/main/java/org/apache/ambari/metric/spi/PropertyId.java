package org.apache.ambari.metric.spi;

/**
 * The property id is used to uniquely identify a property.  The property id
 * is a composite of property name and category name.  The property also
 * contains an indicator for temporal values.
 */
public interface PropertyId {

    public String getName();

    public String getCategory();

    public boolean isTemporal();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object o);
}
