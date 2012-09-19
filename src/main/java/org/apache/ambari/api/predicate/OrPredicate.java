package org.apache.ambari.api.predicate;

import org.apache.ambari.api.spi.Resource;

/**
 * Predicate which evaluates to true if any of the predicates in a predicate
 * array evaluate to true.
 */
public class OrPredicate extends ArrayPredicate {

    public OrPredicate(BasePredicate ... predicates) {
        super(predicates);
    }

    @Override
    public boolean evaluate(Resource resource) {
        BasePredicate[] predicates = getPredicates();
        for (BasePredicate predicate : predicates) {
            if (predicate.evaluate(resource)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getOperator() {
        return "OR";
    }
}
