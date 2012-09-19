package org.apache.ambari.api.resource;

import org.apache.ambari.api.services.formatters.CollectionFormatter;
import org.apache.ambari.api.services.formatters.HostComponentInstanceFormatter;
import org.apache.ambari.api.services.formatters.ResultFormatter;
import org.apache.ambari.api.spi.PropertyId;
import org.apache.ambari.api.spi.Resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class HostComponentResourceDefinition extends BaseResourceDefinition {

    private String m_clusterId;
    private String m_hostId;

    @Override
    public String getPluralName() {
        return "host_components";
    }

    @Override
    public String getSingularName() {
        return "host_component";
    }

    public HostComponentResourceDefinition(String id, String clusterId, String hostId) {
        super(Resource.Type.HostComponent, id);
        m_clusterId = clusterId;
        m_hostId = hostId;
        setResourceId(Resource.Type.Cluster, m_clusterId);
        setResourceId(Resource.Type.Host, m_hostId);
    }

    @Override
    public Set<ResourceDefinition> getChildren() {
        return Collections.emptySet();
    }

    @Override
    public Set<ResourceDefinition> getRelations() {
        Set<ResourceDefinition> setRelated = new HashSet<ResourceDefinition>();
        // already have all information necessary for host
        //todo: adding host here causes a cycle
        //setRelated.add(new HostResourceDefinition(m_hostId, m_clusterId));
        // for component need service id property
        ComponentResourceDefinition componentResource = new ComponentResourceDefinition(
                getId(), m_clusterId, null);
        PropertyId serviceIdProperty = getClusterController().getSchema(
                Resource.Type.Component).getKeyPropertyId(Resource.Type.Service);
        componentResource.getQuery().addProperty(serviceIdProperty);
        setRelated.add(componentResource);

        return setRelated;
    }

    @Override
    public ResultFormatter getResultFormatter() {
        //todo: instance formatter
        return getId() == null ? new CollectionFormatter(this) : new HostComponentInstanceFormatter(this);
    }
}
