package org.apache.ambari.metric.services.formatters;

import org.apache.ambari.metric.internal.ClusterControllerImpl;
import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.Schema;
import org.codehaus.jackson.annotate.JsonUnwrapped;

import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/15/12
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceInstanceFormatter implements ResultFormatter {

    public String href;
    public List<HrefEntry> components = new ArrayList<HrefEntry>();

    //todo: don't include properties group.  Annotation doesn't seem to work.
    @JsonUnwrapped
    public Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();

    private ResourceDefinition m_resourceDefinition;

    public ServiceInstanceFormatter(ResourceDefinition resourceDefinition) {
        m_resourceDefinition = resourceDefinition;
    }

    @Override
    public Object format(Result result, UriInfo uriInfo) {
        href = uriInfo.getAbsolutePath().toString();

        String itemHref =  href.endsWith("/") ? href : href +'/';

        Map<String, List<Resource>> mapResults = result.getResources();

        List<Resource> listProperties = mapResults.get("/");
        Resource resource = listProperties.get(0);
        properties = resource.getCategories();

        Set<ResourceDefinition> setChildren = m_resourceDefinition.getChildren();
        for(ResourceDefinition resourceDef : setChildren) {
            String resourceName = resourceDef.getPluralName();
            List<Resource> listResources = mapResults.get(resourceName);
            for(Resource r : listResources) {
                Schema schema = ClusterControllerImpl.getSingleton().getSchema(r.getType());
                components.add(new HrefEntry(itemHref + resourceName + '/' +
                        r.getPropertyValue(schema.getKeyPropertyId(r.getType()))));
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
