package org.apache.ambari.metric.services;

import org.apache.ambari.metric.handlers.DelegatingRequestHandler;
import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.ResourceDefinition;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 6/21/12
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseService {

    protected Response handleRequest(HttpHeaders headers, UriInfo uriInfo, Request.RequestType requestType,
                                     ResourceDefinition resourceDefinition) {

        Request req = getRequestFactory().createRequest(headers, uriInfo, requestType, resourceDefinition);
        Result result = getRequestHandler().handleRequest(req);
        return getResponseFactory().createResponse(resourceDefinition.getResultFormatter().format(result, uriInfo));
    }

    RequestFactory getRequestFactory() {
        return new RequestFactory();
    }

    ResponseFactory getResponseFactory() {
        return new ResponseFactory();
    }

    RequestHandler getRequestHandler() {
        return new DelegatingRequestHandler();
    }
}
