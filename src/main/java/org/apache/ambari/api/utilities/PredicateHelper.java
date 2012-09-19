package org.apache.ambari.api.utilities;

import org.apache.ambari.api.predicate.BasePredicate;
import org.apache.ambari.api.spi.Predicate;
import org.apache.ambari.api.spi.PropertyId;

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
