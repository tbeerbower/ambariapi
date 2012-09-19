package org.apache.ambari.metric.spi;

import java.util.Set;

/**
 * The request object contains the property ids of all the properties
 * that are wanted for the query.  The request object also contains any
 * temporal (date range) information, if any, for each requested property.
 */
public interface Request {

    /**
     * The set of property ids being requested.  An empty set signifies
     * that all supported properties should be returned (i.e. select * ).
     *
     * @return the set of property ids being requested
     */
    public Set<PropertyId> getPropertyIds();

    /**
     * Get the {@link TemporalInfo temporal information} for the given property
     * id for this request, if any.
     *
     * @param id  the property id
     *
     * @return the temporal information for the given property id; null if noe exists
     */
    public TemporalInfo getTemporalInfo(PropertyId id);

    /**
     * Temporal request information describing a range and increment of time.
     */
    public static interface TemporalInfo {

        /**
         * Get the start of the requested time range.  The time is given in
         * seconds since the Unix epoch.
         *
         * @return the start time in seconds
         */
        public long getStartTime();

        /**
         * Get the end of the requested time range.  The time is given in
         * seconds since the Unix epoch.
         *
         * @return the end time in seconds
         */
        public long getEndTime();

        /**
         * Get the requested time between each data point of the temporal
         * data.  The time is given in seconds.
         *
         * @return the step time in seconds
         */
        public long getStep();
    }
}
