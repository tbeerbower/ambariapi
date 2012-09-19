package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.Schema;

import javax.ws.rs.core.UriInfo;

/**
 * HostComponent instance formatter.
 */
public class HostComponentInstanceFormatter extends BaseFormatter {
    /**
     * Related host.
     */
    public HrefEntry host;

    /**
     * Related component.
     */
    public HrefEntry component;

    /**
     * Constructor.
     *
     * @param resourceDefinition  the resource definition
     */
    public HostComponentInstanceFormatter(ResourceDefinition resourceDefinition) {
        super(resourceDefinition);
    }

    /**
     * Extends the base format to add the host.
     *
     * @param result   the result being formatted
     * @param uriInfo  url info
     *
     * @return         the formatted hostComponent instance
     */
    @Override
    public Object format(Result result, UriInfo uriInfo) {
        Object o = super.format(result, uriInfo);
        host = new HrefEntry(href.substring(0, href.indexOf("/host_components/")));

        return o;
    }

    /**
     * Add component.
     *
     * @param href  the href to add
     * @param r     the resource being added
     */
    @Override
    public void addSubResource(HrefEntry href, Resource r) {
        component = href;
    }

    /**
     * Build the component href.
     *
     * @param baseHref  base url
     * @param schema    associated schema
     * @param relation  the component resource
     *
     * @return href for the associated component resource
     */
    @Override
    String buildRelationHref(String baseHref, Schema schema, Resource relation) {
        ResourceDefinition resourceDefinition = getResourceDefinition();
        String clusterId = resourceDefinition.getResourceIds().get(Resource.Type.Cluster);
        String serviceId = relation.getPropertyValue(schema.getKeyPropertyId(Resource.Type.Service));
        String componentId = resourceDefinition.getId();
        return href.substring(0, href.indexOf(clusterId) + clusterId.length() + 1) +
                "services/" +serviceId + "/components/" + componentId;
    }
}
