package org.apache.ambari.metric.predicate;

import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

import java.util.Set;

/**
 *
 */
public class NotPredicate implements Predicate {
    private final Predicate predicate;

    public NotPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean apply(Resource resource) {
        return !predicate.apply(resource);
    }

    @Override
    public Set<PropertyId> getPropertyIds() {
        return predicate.getPropertyIds();
    }

    @Override
    public String toSQL() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
