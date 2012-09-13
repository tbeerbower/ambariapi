package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public abstract class ArrayPredicate implements Predicate {
    private final Predicate[] predicates;
    private final Set<PropertyId> propertyIds = new HashSet<PropertyId>();

    public ArrayPredicate(Predicate ... predicates) {
        this.predicates = predicates;
        for (int i = 0; i < predicates.length; i++) {
            propertyIds.addAll(predicates[i].getPropertyIds());
        }
    }

    protected Predicate[] getPredicates() {
        return predicates;
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return propertyIds;
    }

    protected String toSQL(String operator) {
        Predicate[] predicates = getPredicates();
        if (predicates.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < predicates.length; i++) {
            if (i > 0) {
                sb.append(" " + operator + " ");
            }
            sb.append(predicates[i].toSQL());
        }
        sb.append(")");
        return sb.toString();
    }
}
