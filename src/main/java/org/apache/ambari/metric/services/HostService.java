package org.apache.ambari.metric.services;

import org.apache.ambari.metric.resource.HostResourceDefinition;
import org.apache.ambari.metric.resource.ResourceDefinition;

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
     * @param clusterName  cluster id
     */
    public HostService(String clusterName) {
        m_clusterName = clusterName;
    }

    /**
     * Handles URL: /clusters/{clusterID}/hosts/{hostID}
     * Get a specific host.
     *
     * @param headers   http headers
     * @param ui        uri info
     * @param hostName  host id
     *
     * @return host resource representation
     */
    @GET @Path("{hostName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHost(@Context HttpHeaders headers, @Context UriInfo ui,
                            @PathParam("hostName") String hostName) {

        return handleRequest(headers, ui, Request.RequestType.GET,
                createResourceDefinition(hostName, m_clusterName));
    }

    /**
     * Handles URL: /clusters/{clusterID}/hosts or /clusters/hosts
     * Get all hosts for a cluster.
     *
     * @param headers  http headers
     * @param ui       uri info
     *
     * @return host collection resource representation
     */
    @GET @Produces(MediaType.APPLICATION_JSON)
    public Response getHosts(@Context HttpHeaders headers, @Context UriInfo ui) {
        return handleRequest(headers, ui, Request.RequestType.GET, createResourceDefinition(null, m_clusterName));
    }

    /**
     * Get the host_components sub-resource.
     *
     * @param hostName  host id
     *
     * @return the host_components service
     */
    @Path("{hostName}/host_components")
    public HostComponentService getHostComponentHandler(@PathParam("hostName") String hostName) {
        return new HostComponentService(m_clusterName, hostName);
    }

    /**
     * Create a host resource definition.
     *
     * @param hostName     host name
     * @param clusterName  cluster name
     *
     * @return a host resource definition
     */
    private ResourceDefinition createResourceDefinition(String hostName, String clusterName) {
        return new HostResourceDefinition(hostName, clusterName);
    }
}
