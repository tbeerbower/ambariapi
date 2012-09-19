package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.spi.Resource;
import java.util.*;

/**
 * Host instance formatter.
 */
public class HostInstanceFormatter extends BaseFormatter {
    /**
     * host_components collection.
     */
    public List<HrefEntry> host_components = new ArrayList<HrefEntry>();


    /**
     * Constructor.
     *
     * @param resourceDefinition  the resource definition
     */
    public HostInstanceFormatter(ResourceDefinition resourceDefinition) {
        super(resourceDefinition);
    }

    /**
     * Add host_component href's.
     *
     * @param href  the host_component href to add
     * @param r     the host_component resource being added
     */
    @Override
    public void addSubResource(BaseFormatter.HrefEntry href, Resource r) {
        host_components.add(href);
    }
}
