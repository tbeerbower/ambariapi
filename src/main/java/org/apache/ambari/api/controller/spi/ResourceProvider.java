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
 * The resource provider allows for the plugging in of a back end data store
 * for a resource type.  The resource provider is associated with a specific
 * resource type and can be queried for a list of resources of that type.
 * The resource provider plugs into and is used by the
 * {@link ClusterController cluster controller} to obtain a list of resources
 * for a given request.
 */
public interface ResourceProvider {
  /**
   * Get a set of {@link Resource resources} based on the given request and predicate
   * information.
   * </p>
   * Note that it is not required for this resource provider to completely filter
   * the set of resources based on the given predicate.  It may not be possible
   * since some of the properties involved may be provided by another
   * {@link PropertyProvider provider}.  This partial filtering is allowed because
   * the predicate will always be applied by the calling cluster controller.  The
   * predicate is made available at this level so that some pre-filtering can be done
   * as an optimization.
   * </p>
   * A simple implementation of a resource provider may choose to just return all of
   * the resources of a given type and allow the calling cluster controller to filter
   * based on the predicate.
   *
   * @param request   the request object which defines the desired set of properties
   * @param predicate the predicate object which can be used to filter which
   *                  resources are returned
   * @return a set of resources based on the given request and predicate information
   */
  public Set<Resource> getResources(Request request, Predicate predicate);

  /**
   * Get the set of property ids for the properties that this provider can provide.
   *
   * @return the set of property ids for the properties that this provider can provide
   */
  public Set<PropertyId> getPropertyIds();
}
