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

import org.apache.ambari.api.resource.HostResourceDefinition;
import org.apache.ambari.api.resource.ResourceDefinition;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Service responsible for hosts resource requests.
 */
public class HostService extends BaseService {

  /**
   * Parent cluster id.
   */
  private String m_clusterName;

  /**
   * Constructor.
   *
   * @param clusterName cluster id
   */
  public HostService(String clusterName) {
    m_clusterName = clusterName;
  }

  /**
   * Handles URL: /clusters/{clusterID}/hosts/{hostID}
   * Get a specific host.
   *
   * @param headers  http headers
   * @param ui       uri info
   * @param hostName host id
   * @return host resource representation
   */
  @GET
  @Path("{hostName}")
  @Produces("text/plain")
  public Response getHost(@Context HttpHeaders headers, @Context UriInfo ui,
                          @PathParam("hostName") String hostName) {

    return handleRequest(headers, ui, Request.RequestType.GET,
        createResourceDefinition(hostName, m_clusterName));
  }

  /**
   * Handles URL: /clusters/{clusterID}/hosts or /clusters/hosts
   * Get all hosts for a cluster.
   *
   * @param headers http headers
   * @param ui      uri info
   * @return host collection resource representation
   */
  @GET
  @Produces("text/plain")
  public Response getHosts(@Context HttpHeaders headers, @Context UriInfo ui) {
    return handleRequest(headers, ui, Request.RequestType.GET, createResourceDefinition(null, m_clusterName));
  }

  /**
   * Get the host_components sub-resource.
   *
   * @param hostName host id
   * @return the host_components service
   */
  @Path("{hostName}/host_components")
  public HostComponentService getHostComponentHandler(@PathParam("hostName") String hostName) {
    return new HostComponentService(m_clusterName, hostName);
  }

  /**
   * Create a host resource definition.
   *
   * @param hostName    host name
   * @param clusterName cluster name
   * @return a host resource definition
   */
  ResourceDefinition createResourceDefinition(String hostName, String clusterName) {
    return new HostResourceDefinition(hostName, clusterName);
  }
}
