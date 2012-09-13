package org.apache.ambari.metric.spi;

import java.util.Set;

/**
 * The request object contains the property ids of all the properties
 * that are wanted for the query.  The request object also contains any
 * temporal (date range) information, if any, for each requested property.
 */
public interface Request {

    public Set<PropertyId> getPropertyIds();

    public TemporalInfo getTemporalInfo(PropertyId id);

    public static interface TemporalInfo {
        public long getStartTime();

        public long getEndTime();

        public long getStep();
    }
}
