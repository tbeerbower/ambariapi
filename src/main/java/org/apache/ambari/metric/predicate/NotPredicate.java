package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Resource;

/**
 * Predicate that negates the evaluation of another predicate.
 */
public class NotPredicate extends UnaryPredicate {

    public NotPredicate(BasePredicate predicate) {
        super(predicate);
    }

    @Override
    public boolean evaluate(Resource resource) {
        return !getPredicate().evaluate(resource);
    }

    @Override
    public String getOperator() {
        return "NOT";
    }
}
