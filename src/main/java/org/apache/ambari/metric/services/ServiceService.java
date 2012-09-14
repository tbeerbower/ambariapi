package org.apache.ambari.metric.services;

import org.apache.ambari.metric.resource.ServiceResourceDefinition;

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
     * @param clusterName  cluster id
     */
    public ServiceService(String clusterName) {
        m_clusterName = clusterName;
    }

    /**
     * Handles URL: /clusters/{clusterID}/services/{serviceID}
     * Get a specific service.
     *
     * @param headers      http headers
     * @param ui           uri info
     * @param serviceName  service id
     *
     * @return service resource representation
     */
    @GET @Path("{serviceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getService(@Context HttpHeaders headers, @Context UriInfo ui,
                            @PathParam("serviceName") String serviceName) {

        return handleRequest(headers, ui, Request.RequestType.GET,
                new ServiceResourceDefinition(serviceName, m_clusterName));
    }

    /**
     * Handles URL: /clusters/{clusterID}/services
     * Get all services for a cluster.
     *
     * @param headers  http headers
     * @param ui       uri info
     *
     * @return service collection resource representation
     */
    @GET @Produces(MediaType.APPLICATION_JSON)
    public Response getServices(@Context HttpHeaders headers, @Context UriInfo ui) {
        return handleRequest(headers, ui, Request.RequestType.GET,
                new ServiceResourceDefinition(null, m_clusterName));
    }

    /**
     * Get the components sub-resource.
     *
     * @param serviceName  service id
     *
     * @return the components service
     */
    @Path("{serviceName}/components")
    public ComponentService getComponentHandler(@PathParam("serviceName") String serviceName) {

        return new ComponentService(m_clusterName, serviceName);
    }
}
