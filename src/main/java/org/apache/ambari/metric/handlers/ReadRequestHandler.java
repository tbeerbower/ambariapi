package org.apache.ambari.metric.handlers;

import org.apache.ambari.metric.services.Request;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.query.Query;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/5/12
 * Time: 12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadRequestHandler implements RequestHandler {

    @Override
    public Result handleRequest(Request request) {
        Query query = request.getResource().getQuery();

        // would need to account for PR and expansion here

        return query.execute();
    }
}
