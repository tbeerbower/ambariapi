package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.Resource;


/**
 * Predicate which evaluates to true if all of the predicates in a predicate
 * array evaluate to true.
 */
public class AndPredicate extends ArrayPredicate {

    public AndPredicate(BasePredicate ... predicates) {
        super(predicates);
    }

    @Override
    public boolean evaluate(Resource resource) {
        Predicate[] predicates = getPredicates();
        for (Predicate predicate : predicates) {
            if (!predicate.evaluate(resource)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getOperator() {
        return "AND";
    }
}
