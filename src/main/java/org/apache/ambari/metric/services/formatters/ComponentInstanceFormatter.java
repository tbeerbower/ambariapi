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
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComponentInstanceFormatter extends BaseFormatter {
    public String href;

    //todo: don't include properties group.  Annotation doesn't seem to work.
    @JsonUnwrapped
    public Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
    public List<HrefEntry> host_components = new ArrayList<HrefEntry>();
    private ResourceDefinition m_resourceDefinition;


    public ComponentInstanceFormatter(ResourceDefinition resourceDefinition) {
        m_resourceDefinition = resourceDefinition;
    }

    @Override
    public Object format(Result result, UriInfo uriInfo) {
        href = uriInfo.getAbsolutePath().toString();
        Map<String, List<Resource>> mapResults = result.getResources();

        List<Resource> listProperties = mapResults.get("/");
        Resource resource = listProperties.get(0);
        properties = resource.getCategories();

        Set<ResourceDefinition> setChildren = m_resourceDefinition.getRelatedResources();
        for(ResourceDefinition resourceDef : setChildren) {
            //todo: name is singular
            String resourceName = resourceDef.getSingularName();
            List<Resource> listResources = mapResults.get(resourceName);
            for(Resource r : listResources) {
                Schema schema = ClusterControllerImpl.getSingleton().getSchema(r.getType());
                String clusterId = m_resourceDefinition.getResourceIds().get(Resource.Type.Cluster);
                String hostComponentUrl = href.substring(0, href.indexOf(clusterId) + clusterId.length() + 1) +
                        "hosts/" + r.getPropertyValue(schema.getKeyPropertyId(Resource.Type.Host)) +
                        "/host_components/" + m_resourceDefinition.getId();

                host_components.add(new HrefEntry(hostComponentUrl));
            }
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
