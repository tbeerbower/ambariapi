package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.Resource;


/**
 *
 */
public class AndPredicate extends ArrayPredicate {

    public AndPredicate(Predicate ... predicates) {
        super(predicates);
    }

    @Override
    public boolean apply(Resource resource) {
        Predicate[] predicates = getPredicates();
        for (int i = 0; i < predicates.length; i++) {
            if (!predicates[i].apply(resource)) {
                 return false;
            }
        }
        return true;
    }

    @Override
    public String toSQL() throws UnsupportedOperationException {
        return toSQL("AND");
    }
}
