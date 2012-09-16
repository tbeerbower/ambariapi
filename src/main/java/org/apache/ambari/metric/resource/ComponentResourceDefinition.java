package org.apache.ambari.metric.resource;

import org.apache.ambari.metric.services.formatters.CollectionFormatter;
import org.apache.ambari.metric.services.formatters.ComponentInstanceFormatter;
import org.apache.ambari.metric.services.formatters.InstanceFormatter;
import org.apache.ambari.metric.services.formatters.ResultFormatter;
import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.Resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComponentResourceDefinition extends BaseResourceDefinition {

    private String m_clusterId;
    private String m_serviceId;

    @Override
    public String getPluralName() {
        return "components";
    }

    @Override
    public String getSingularName() {
        return "component";
    }

    public ComponentResourceDefinition(String id, String clusterId, String serviceId) {
        super(Resource.Type.Component, id);
        m_clusterId = clusterId;
        m_serviceId = serviceId;
        setResourceId(Resource.Type.Cluster, m_clusterId);
        setResourceId(Resource.Type.Service, m_serviceId);
    }
    @Override
    public Set<ResourceDefinition> getChildren() {
        return Collections.emptySet();
    }

    @Override
    public Set<ResourceDefinition> getRelatedResources() {
        Set<ResourceDefinition> setResourceDefinitions = new HashSet<ResourceDefinition>();
        // for host_component collection need host id property
        HostComponentResourceDefinition hostComponentResource = new HostComponentResourceDefinition(
                getId(), m_clusterId, null);
        PropertyId hostIdProperty = getClusterController().getSchema(
                Resource.Type.HostComponent).getKeyPropertyId(Resource.Type.Host);
        hostComponentResource.getQuery().addProperty(hostIdProperty);
        setResourceDefinitions.add(hostComponentResource);
        return setResourceDefinitions;
    }

    @Override
    public ResultFormatter getResultFormatter() {
        //todo: instance formatter
        return getId() == null ? new CollectionFormatter(this) : new ComponentInstanceFormatter(this);
    }
}
