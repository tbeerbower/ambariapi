package org.apache.ambari.api.services;

import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResponseFactory {
    public Response createResponse(Object result) {
        return Response.ok(result).build();
    }
}