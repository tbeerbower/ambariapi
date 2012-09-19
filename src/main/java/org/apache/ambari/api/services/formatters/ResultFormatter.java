package org.apache.ambari.api.services.formatters;


import org.apache.ambari.api.services.Result;

import javax.ws.rs.core.UriInfo;

/**
 * Format internal result to format expected by client.
 */
public interface ResultFormatter {
    /**
     * Format the given result to a format expected by client.
     *
     * @param result   internal result
     * @param uriInfo  URL info for request
     *
     * @return the formatted result
     */
    Object format(Result result, UriInfo uriInfo);
}
