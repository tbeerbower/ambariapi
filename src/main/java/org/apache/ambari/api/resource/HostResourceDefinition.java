package org.apache.ambari.api.resource;

import org.apache.ambari.api.services.formatters.CollectionFormatter;
import org.apache.ambari.api.services.formatters.HostInstanceFormatter;
import org.apache.ambari.api.services.formatters.ResultFormatter;
import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class HostResourceDefinition extends BaseResourceDefinition {

    private String m_clusterId;

    @Override
    public String getPluralName() {
        return "hosts";
    }

    @Override
    public String getSingularName() {
        return "host";
    }

    public HostResourceDefinition(String id, String clusterId) {
        super(Resource.Type.Host, id);
        m_clusterId = clusterId;
        setResourceId(Resource.Type.Cluster, m_clusterId);
    }

    @Override
    public Set<ResourceDefinition> getChildren() {
        Set<ResourceDefinition> setChildren = new HashSet<ResourceDefinition>();

        HostComponentResourceDefinition hostComponentResource = new HostComponentResourceDefinition(
                null, m_clusterId, getId());
        PropertyId hostComponentIdProperty = getClusterController().getSchema(
                Resource.Type.HostComponent).getKeyPropertyId(Resource.Type.HostComponent) ;
        hostComponentResource.getQuery().addProperty(hostComponentIdProperty);
        setChildren.add(hostComponentResource);
        return setChildren;
    }

    @Override
    public Set<ResourceDefinition> getRelations() {
        return Collections.emptySet();
    }

    @Override
    public ResultFormatter getResultFormatter() {
        //todo: instance formatter
        return getId() == null ? new CollectionFormatter(this) : new HostInstanceFormatter(this);
    }
}
