package org.apache.ambari.api.handlers;

import org.apache.ambari.api.services.Request;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestHandlerFactory {
    public RequestHandler getRequestHandler(Request.RequestType requestType) {
        switch (requestType) {
            case GET:
                return new ReadRequestHandler();
            default:
                //todo:
                throw new UnsupportedOperationException("Only GET requests are supported at this time");
        }
    }
}
