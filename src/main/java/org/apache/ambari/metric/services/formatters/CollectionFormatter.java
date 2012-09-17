package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.internal.ClusterControllerImpl;
import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.Schema;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Result formatter for collection resources.
 */
public class CollectionFormatter extends BaseFormatter {
    public String href;
    public List<HrefEntry> items = new ArrayList<HrefEntry>();
    private ResourceDefinition m_resourceDefinition;
    private Schema m_schema;

    public CollectionFormatter(ResourceDefinition resourceDefinition) {
        m_resourceDefinition = resourceDefinition;
        m_schema = ClusterControllerImpl.getSingleton().getSchema(resourceDefinition.getType());

    }

    @Override
    public Object format(Result result, UriInfo uriInfo) {
        href = uriInfo.getAbsolutePath().toString();
        Map<String, List<Resource>> mapResults = result.getResources();
        List<Resource> listResources = mapResults.get("/");

        for (Resource r : listResources) {
            String itemHref =  href.endsWith("/") ? href : href +'/';

            items.add(new HrefEntry(itemHref + r.getPropertyValue(m_schema.getKeyPropertyId(
                    m_resourceDefinition.getType()))));
        }
        return serialize(this);
    }

    public static class HrefEntry {
        public String href;

        public HrefEntry(String href) {
            this.href = href;
        }
    }
}
