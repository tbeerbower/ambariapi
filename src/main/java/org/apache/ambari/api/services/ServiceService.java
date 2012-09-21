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

package org.apache.ambari.api.services;

import org.apache.ambari.api.resource.ResourceDefinition;
import org.apache.ambari.api.resource.ServiceResourceDefinition;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Service responsible for services resource requests.
 */
public class ServiceService extends BaseService {
  /**
   * Parent cluster name.
   */
  private String m_clusterName;

  /**
   * Constructor.
   *
   * @param clusterName cluster id
   */
  public ServiceService(String clusterName) {
    m_clusterName = clusterName;
  }

  /**
   * Handles URL: /clusters/{clusterID}/services/{serviceID}
   * Get a specific service.
   *
   * @param headers     http headers
   * @param ui          uri info
   * @param serviceName service id
   * @return service resource representation
   */
  @GET
  @Path("{serviceName}")
  @Produces("text/plain")
  public Response getService(@Context HttpHeaders headers, @Context UriInfo ui,
                             @PathParam("serviceName") String serviceName) {

    return handleRequest(headers, ui, Request.RequestType.GET,
        createResourceDefinition(serviceName, m_clusterName));
  }

  /**
   * Handles URL: /clusters/{clusterID}/services
   * Get all services for a cluster.
   *
   * @param headers http headers
   * @param ui      uri info
   * @return service collection resource representation
   */
  @GET
  @Produces("text/plain")
  public Response getServices(@Context HttpHeaders headers, @Context UriInfo ui) {
    return handleRequest(headers, ui, Request.RequestType.GET,
        createResourceDefinition(null, m_clusterName));
  }

  /**
   * Get the components sub-resource.
   *
   * @param serviceName service id
   * @return the components service
   */
  @Path("{serviceName}/components")
  public ComponentService getComponentHandler(@PathParam("serviceName") String serviceName) {

    return new ComponentService(m_clusterName, serviceName);
  }

  /**
   * Create a service resource definition.
   *
   * @param serviceName host name
   * @param clusterName cluster name
   * @return a service resource definition
   */
  ResourceDefinition createResourceDefinition(String serviceName, String clusterName) {
    return new ServiceResourceDefinition(serviceName, clusterName);
  }
}
