package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.spi.Resource;

import java.util.*;

/**
 * Formatter for cluster instance resources.
 */
public class ClusterInstanceFormatter extends BaseFormatter {

    /**
     * services collection
     */
    public List<HrefEntry> services = new ArrayList<HrefEntry>();

    /**
     * hosts collection
     */
    public List<HrefEntry> hosts = new ArrayList<HrefEntry>();

    /**
     * Constructor.
     *
     * @param resourceDefinition  resource definition
     */
    public ClusterInstanceFormatter(ResourceDefinition resourceDefinition) {
       super(resourceDefinition);
    }

    /**
     * Add services and hosts href's.
     *
     * @param href  the href to add
     * @param r     the resource being added
     */
    @Override
    public void addSubResource(HrefEntry href, Resource r) {
        if (r.getType() == Resource.Type.Service) {
            services.add(href);
        } else {
            hosts.add(href);
        }
    }
}
