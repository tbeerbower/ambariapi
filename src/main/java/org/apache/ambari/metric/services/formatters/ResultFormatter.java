package org.apache.ambari.metric.services.formatters;


import org.apache.ambari.metric.services.Result;

import javax.ws.rs.core.UriInfo;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/14/12
 * Time: 7:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultFormatter {
    Object format(Result result, UriInfo uriInfo);
}
