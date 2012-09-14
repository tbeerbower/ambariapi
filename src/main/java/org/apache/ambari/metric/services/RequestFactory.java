package org.apache.ambari.metric.services;

import org.apache.ambari.metric.resource.ResourceDefinition;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 8:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestFactory {
    public Request createRequest(HttpHeaders headers, UriInfo uriInfo, Request.RequestType requestType,
                                 ResourceDefinition resourceDefinition) {

        return new RequestImpl(headers, uriInfo, requestType, resourceDefinition);
    }
}
