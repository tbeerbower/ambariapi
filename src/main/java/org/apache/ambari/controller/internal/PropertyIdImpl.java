package org.apache.ambari.controller.internal;

import org.apache.ambari.controller.spi.PropertyId;

/**
 *
 */
public class PropertyIdImpl implements PropertyId {
    private String name;
    private String category;
    private boolean temporal;

    public PropertyIdImpl() {

    }

    public PropertyIdImpl(String name, String category, boolean temporal) {
        this.name = name;
        this.category = category;
        this.temporal = temporal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isTemporal() {
        return temporal;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + (category == null ? 0 : category.hashCode()) + (temporal ? 1 : 0);
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof PropertyIdImpl)) {
            return false;
        }
        PropertyIdImpl that = (PropertyIdImpl) o;

        return this.name.equals(that.getName()) &&
                equals(this.category, that.getCategory()) &&
                this.isTemporal() == that.isTemporal();
    }

    private static boolean equals(Object o1, Object o2) {
        if ( o1 == null ) {
            return o2 == null;
        }

        if ( o2 == null ) {
            return o1 == null;
        }

        return o1.equals(o2);
    }


    @Override
    public String toString() {
        return "PropertyId[" + category + ", " + name +"]";
    }
}
