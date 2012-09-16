package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.internal.ClusterControllerImpl;
import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.Schema;

import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/14/12
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterInstanceFormatter implements ResultFormatter {
    public String href;
    public List<HrefEntry> services = new ArrayList<HrefEntry>();
    public List<HrefEntry> hosts = new ArrayList<HrefEntry>();

    private ResourceDefinition m_resourceDefinition;

    public ClusterInstanceFormatter(ResourceDefinition resourceDefinition) {
        m_resourceDefinition = resourceDefinition;
    }

    @Override
    public Object format(Result result, UriInfo uriInfo) {
        href = uriInfo.getAbsolutePath().toString();

        String itemHref =  href.endsWith("/") ? href : href +'/';
        Map<String, List<Resource>> mapResults = result.getResources();

        Set<ResourceDefinition> setChildren = m_resourceDefinition.getChildren();
        for(ResourceDefinition resource : setChildren) {
            String resourceName = resource.getPluralName();
            List<Resource> listResources = mapResults.get(resourceName);
            for(Resource r : listResources) {
                Schema schema = ClusterControllerImpl.getSingleton().getSchema(r.getType());
                if (resourceName == "services") {
                    services.add(new HrefEntry(itemHref + resourceName + '/' +
                            r.getPropertyValue(schema.getKeyPropertyId(r.getType()))));
                } else {
                    hosts.add(new HrefEntry(itemHref + resourceName + '/' +
                            r.getPropertyValue(schema.getKeyPropertyId(r.getType()))));
                }

            }
        }

        return this;
    }

    public static class HrefEntry {
        public String href;

        public HrefEntry(String href) {
            this.href = href;
        }
    }


}
