package org.apache.ambari.metric.services;

import org.apache.ambari.metric.resource.ComponentResourceDefinition;
import org.apache.ambari.metric.resource.ResourceDefinition;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Service responsible for components resource requests.
 */
public class ComponentService extends BaseService {
    /**
     * Parent cluster id.
     */
    private String m_clusterName;

    /**
     * Parent service id.
     */
    private String m_serviceName;

    /**
     * Constructor.
     *
     * @param clusterName  cluster id
     * @param serviceName  service id
     */
    public ComponentService(String clusterName, String serviceName) {
        m_clusterName = clusterName;
        m_serviceName = serviceName;
    }

    /**
     * Handles URL: /clusters/{clusterID}/services/{serviceID}/components/{componentID}
     * Get a specific component.
     *
     * @param headers        http headers
     * @param ui             uri info
     * @param componentName  component id
     *
     * @return a component resource representation
     */
    @GET @Path("{componentName}")
    @Produces("text/plain")
    public Response getComponent(@Context HttpHeaders headers, @Context UriInfo ui,
                                 @PathParam("componentName") String componentName) {

        return handleRequest(headers, ui, Request.RequestType.GET,
                createResourceDefinition(componentName, m_clusterName, m_serviceName));
    }

    /**
     * Handles URL: /clusters/{clusterID}/services/{serviceID}/components
     * Get all components for a service.
     *
     * @param headers  http headers
     * @param ui       uri info
     *
     * @return component collection resource representation
     */
    @GET
    @Produces("text/plain")
    public Response getComponents(@Context HttpHeaders headers, @Context UriInfo ui) {
        return handleRequest(headers, ui, Request.RequestType.GET,
                createResourceDefinition(null, m_clusterName, m_serviceName));
    }

    /**
     * Create a component resource definition.
     *
     * @param clusterName    cluster name
     * @param serviceName    service name
     * @param componentName  component name
     *
     * @return a component resource definition
     */
    ResourceDefinition createResourceDefinition(String clusterName, String serviceName, String componentName) {
        return new ComponentResourceDefinition(clusterName, serviceName, componentName);
    }
}
