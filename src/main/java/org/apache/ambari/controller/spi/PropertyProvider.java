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
package org.apache.ambari.controller.spi;

import java.util.Set;

/**
 * The property provider is used to plug in various property sources into a
 * resource provider.  The property provider is able to populate, or partially
 * populate a given resource object with property values.
 */
public interface PropertyProvider {

    /**
     * Populate the given resource with any properties that this property
     * provider can provide.
     *
     * @param resource   the resource to be populated
     * @param request    the request object which defines the desired set of properties
     * @param predicate  the predicate object which filters which resources are returned
     */
    public void populateResource(Resource resource, Request request, Predicate predicate);

    /**
     * Get the set of property ids for the properties that this provider can provide.
     *
     * @return the set of property ids for the properties that this provider can provide
     */
    public Set<PropertyId> getPropertyIds();
}
