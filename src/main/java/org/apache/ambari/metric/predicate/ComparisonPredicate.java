package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.PropertyId;

import java.util.Comparator;

/**
 *
 */
public abstract class ComparisonPredicate extends PropertyPredicate {
    private final String value;
    private final Comparator<String> comparator;

    public ComparisonPredicate(PropertyId propertyId, String value, Comparator<String> comparator) {
        super(propertyId);
        this.value = value;
        this.comparator = comparator;
    }

    protected String getValue() {
        return value;
    }

    protected Comparator<String> getComparator() {
        return comparator;
    }

    public String toSQL(String operator) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toSQL());
        sb.append(" " + operator + " \"");
        sb.append(value);
        sb.append("\"");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparisonPredicate)) return false;
        if (!super.equals(o)) return false;

        ComparisonPredicate that = (ComparisonPredicate) o;

        if (comparator != null ? !comparator.equals(that.comparator) : that.comparator != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (comparator != null ? comparator.hashCode() : 0);
        return result;
    }
}
