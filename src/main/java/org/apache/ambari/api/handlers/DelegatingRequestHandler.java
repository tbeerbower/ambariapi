package org.apache.ambari.api.handlers;

import org.apache.ambari.api.services.Request;
import org.apache.ambari.api.services.Result;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/6/12
 * Time: 7:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatingRequestHandler implements RequestHandler {
    @Override
    public Result handleRequest(Request request) {
        return getRequestHandlerFactory().getRequestHandler(request.getRequestType()).handleRequest(request);
    }

    RequestHandlerFactory getRequestHandlerFactory() {
        return new RequestHandlerFactory();
    }
}
