package org.apache.ambari.controller.utilities;

import org.apache.ambari.controller.predicate.BasePredicate;
import org.apache.ambari.controller.spi.Predicate;
import org.apache.ambari.controller.spi.PropertyId;

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
