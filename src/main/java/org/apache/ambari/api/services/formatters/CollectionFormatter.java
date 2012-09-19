package org.apache.ambari.api.services.formatters;
import org.apache.ambari.api.resource.ResourceDefinition;
import org.apache.ambari.api.spi.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Result formatter for collection resources.
 */
public class CollectionFormatter extends BaseFormatter {
    /**
     * Items collection
     */
    public List<HrefEntry> items = new ArrayList<HrefEntry>();

    /**
     * Constructor.
     *
     * @param resourceDefinition  resource definition
     */
    public CollectionFormatter(ResourceDefinition resourceDefinition) {
        super(resourceDefinition);
    }

    /**
     * Add an item.
     *
     * @param href  the href to add
     * @param r     the resource being added
     */
    @Override
    public void addSubResource(HrefEntry href, Resource r) {
        items.add(href);
    }

    /**
     * No-op.  Collections do not have properties.
     *
     * @param mapResults  results
     */
    @Override
    void handleProperties(Map<String, List<Resource>> mapResults) {
        // no-op no properties for collection resources
    }
}
