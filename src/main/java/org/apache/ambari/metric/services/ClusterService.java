package org.apache.ambari.metric.services;

import org.apache.ambari.metric.resource.ClusterResourceDefinition;
import org.apache.ambari.metric.resource.ResourceDefinition;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Service responsible for cluster resource requests.
 */
@Path("/clusters/")
public class ClusterService extends BaseService {

    /**
     * Handles URL: /clusters/{clusterID}
     * Get a specific cluster.
     *
     * @param headers      http headers
     * @param ui           uri info
     * @param clusterName  cluster id
     *
     * @return cluster instance representation
     */
    @GET @Path("{clusterName}")
    @Produces("text/plain")
    public Response getCluster(@Context HttpHeaders headers, @Context UriInfo ui,
                               @PathParam("clusterName") String clusterName) {

        return handleRequest(headers, ui, Request.RequestType.GET, createResourceDefinition(clusterName));
    }

    /**
     * Handles URL:  /clusters
     * Get all clusters.
     *
     * @param headers  http headers
     * @param ui       uri info
     *
     * @return cluster collection resource representation
     */
    @GET
    @Produces("text/plain")
    public Response getClusters(@Context HttpHeaders headers, @Context UriInfo ui) {
        return handleRequest(headers, ui, Request.RequestType.GET, createResourceDefinition(null));
    }

    /**
     * Get the hosts sub-resource
     *
     * @param clusterName  cluster id
     *
     * @return the hosts service
     */
    @Path("{clusterName}/hosts")
    public HostService getHostHandler(@PathParam("clusterName") String clusterName) {
        return new HostService(clusterName);
    }

    /**
     * Get the services sub-resource
     *
     * @param clusterName  cluster id
     *
     * @return the services service
     */
    @Path("{clusterName}/services")
    public ServiceService getServiceHandler(@PathParam("clusterName") String clusterName) {
        return new ServiceService(clusterName);
    }

    /**
     * Create a cluster resource definition.
     *
     * @param clusterName  cluster name
     *
     * @return a cluster resource definition
     */
    ResourceDefinition createResourceDefinition(String clusterName) {
        return new ClusterResourceDefinition(clusterName);
    }
}
