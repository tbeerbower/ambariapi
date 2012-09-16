package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.Result;

import javax.ws.rs.core.UriInfo;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/14/12
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstanceFormatter implements ResultFormatter {

    public String href;

    private ResourceDefinition m_resourceDefinition;

    public InstanceFormatter(ResourceDefinition resourceDefinition) {
        m_resourceDefinition = resourceDefinition;
    }

    @Override
    public Object format(Result result, UriInfo uriInfo) {

        href = uriInfo.getAbsolutePath().toString();



        return result;
    }
}
