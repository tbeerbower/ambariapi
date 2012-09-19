package org.apache.ambari.api.services.formatters;

import org.apache.ambari.api.resource.ResourceDefinition;
import org.apache.ambari.api.spi.Resource;
import java.util.*;

/**
 * Service instance formatter.
 */
public class ServiceInstanceFormatter extends BaseFormatter {
    /**
     * components collection.
     */
    public List<HrefEntry> components = new ArrayList<HrefEntry>();

    /**
     * Constructor.
     *
     * @param resourceDefinition  the resource definition
     */
    public ServiceInstanceFormatter(ResourceDefinition resourceDefinition) {
        super(resourceDefinition);
    }

    /**
     * Add component href's.
     *
     * @param href  the component href to add
     * @param r     the component resource being added
     */
    @Override
    public void addSubResource(BaseFormatter.HrefEntry href, Resource r) {
        components.add(href);
    }

}
