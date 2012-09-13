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
}
