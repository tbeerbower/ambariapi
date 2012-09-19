package org.apache.ambari.api.services;

import org.apache.ambari.api.handlers.DelegatingRequestHandler;
import org.apache.ambari.api.handlers.RequestHandler;
import org.apache.ambari.api.resource.ResourceDefinition;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
        Object formattedResult = resourceDefinition.getResultFormatter().format(result, uriInfo);
        return getResponseFactory().createResponse(req.getSerializer().serialize(formattedResult));
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
