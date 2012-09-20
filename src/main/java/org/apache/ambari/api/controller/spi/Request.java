/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ambari.api.controller.spi;

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
   * @param id the property id
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
