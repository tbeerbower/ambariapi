package org.apache.ambari.metric.utilities;

import org.apache.ambari.metric.predicate.BasePredicate;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.PropertyId;

import java.util.Collections;
import java.util.Set;

/**
 *
 */
public class PredicateHelper {

    public static Set<PropertyId> getPropertyIds(Predicate predicate) {
        if (predicate instanceof BasePredicate) {
            return ((BasePredicate) predicate).getPropertyIds();
        }
        return Collections.emptySet();
    }
}
