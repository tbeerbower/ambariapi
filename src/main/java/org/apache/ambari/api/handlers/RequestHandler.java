package org.apache.ambari.api.handlers;

import org.apache.ambari.api.services.Request;
import org.apache.ambari.api.services.Result;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/5/12
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RequestHandler {
    public Result handleRequest(Request request);
}
