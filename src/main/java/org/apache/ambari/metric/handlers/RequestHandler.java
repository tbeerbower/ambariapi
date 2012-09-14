package org.apache.ambari.metric.handlers;

import org.apache.ambari.metric.services.Request;
import org.apache.ambari.metric.services.Result;

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
