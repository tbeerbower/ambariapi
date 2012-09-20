package org.apache.ambari.api.services.formatters;

import org.apache.ambari.api.resource.ResourceDefinition;
import org.apache.ambari.controller.spi.Resource;
import org.apache.ambari.controller.spi.Schema;
import java.util.*;

/**
 * Component instance formatter.
 */
public class ComponentInstanceFormatter extends BaseFormatter {

    /**
     * host_components collection
     */
    public List<HrefEntry> host_components = new ArrayList<HrefEntry>();

    /**
     * Constructor.
     *
     * @param resourceDefinition  resource definition
     */
    public ComponentInstanceFormatter(ResourceDefinition resourceDefinition) {
        super(resourceDefinition);
    }

    /**
     * Add host_components href's.
     *
     * @param href  the href to add
     * @param r     the type of resource being added
     */
    @Override
    public void addSubResource(HrefEntry href, Resource r) {
        host_components.add(href);
    }

    /**
     * Build hosts_component href's.
     *
     * @param baseHref   the base URL
     * @param schema     associated schema
     * @param relation   the host_component resource
     *
     * @return  href for a host_component
     */
    @Override
    String buildRelationHref(String baseHref, Schema schema, Resource relation) {
        ResourceDefinition resourceDefinition = getResourceDefinition();
        String clusterId = resourceDefinition.getResourceIds().get(Resource.Type.Cluster);
        return baseHref.substring(0, baseHref.indexOf(clusterId) + clusterId.length() + 1) +
                "hosts/" + relation.getPropertyValue(schema.getKeyPropertyId(Resource.Type.Host)) +
                "/host_components/" + resourceDefinition.getId();
    }
}
