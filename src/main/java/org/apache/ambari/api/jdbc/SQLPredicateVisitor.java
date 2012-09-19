package org.apache.ambari.api.jdbc;

import org.apache.ambari.api.predicate.ArrayPredicate;
import org.apache.ambari.api.predicate.BasePredicate;
import org.apache.ambari.api.predicate.ComparisonPredicate;
import org.apache.ambari.api.predicate.PredicateVisitor;
import org.apache.ambari.api.predicate.UnaryPredicate;
import org.apache.ambari.api.spi.PropertyId;

/**
 *
 */
public class SQLPredicateVisitor implements PredicateVisitor {

    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void acceptComparisonPredicate(ComparisonPredicate predicate) {
        PropertyId propertyId = predicate.getPropertyId();

        if (propertyId.getCategory() != null) {
            stringBuilder.append(propertyId.getCategory()).append(".");
        }
        stringBuilder.append(propertyId.getName());

        stringBuilder.append(" ").append(predicate.getOperator()).append(" \"");
        stringBuilder.append(predicate.getValue());
        stringBuilder.append("\"");

    }

    @Override
    public void acceptArrayPredicate(ArrayPredicate predicate) {
        BasePredicate[] predicates = predicate.getPredicates();
        if (predicates.length > 0) {

            stringBuilder.append("(");
            for (int i = 0; i < predicates.length; i++) {
                if (i > 0) {
                    stringBuilder.append(" ").append(predicate.getOperator()).append(" ");
                }
                predicates[i].accept(this);
            }
            stringBuilder.append(")");
        }
    }

    @Override
    public void acceptUnaryPredicate(UnaryPredicate predicate) {
        stringBuilder.append(predicate.getOperator()).append("(");
        predicate.getPredicate().accept(this);
        stringBuilder.append(")");
    }


    public String getSQL() {
        return stringBuilder.toString();
    }
}