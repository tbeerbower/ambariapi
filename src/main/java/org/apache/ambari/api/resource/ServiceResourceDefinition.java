package org.apache.ambari.api.resource;

import org.apache.ambari.api.services.formatters.CollectionFormatter;
import org.apache.ambari.api.services.formatters.ResultFormatter;
import org.apache.ambari.api.services.formatters.ServiceInstanceFormatter;
import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceResourceDefinition extends BaseResourceDefinition {

    private String m_clusterId;

    @Override
    public String getPluralName() {
        return "services";
    }

    @Override
    public String getSingularName() {
        return "service";
    }

    public ServiceResourceDefinition(String id, String clusterId) {
        super(Resource.Type.Service, id);
        m_clusterId = clusterId;
        setResourceId(Resource.Type.Cluster, m_clusterId);
    }

    @Override
    public Set<ResourceDefinition> getChildren() {
        Set<ResourceDefinition> setChildren = new HashSet<ResourceDefinition>();
        // for component collection need id property
        ComponentResourceDefinition componentResourceDefinition =
                new ComponentResourceDefinition(null, m_clusterId, getId());
        PropertyId componentIdProperty = getClusterController().getSchema(
                Resource.Type.Component).getKeyPropertyId(Resource.Type.Component);
        componentResourceDefinition.getQuery().addProperty(componentIdProperty);
        setChildren.add(componentResourceDefinition);
        return setChildren;
    }

    @Override
    public Set<ResourceDefinition> getRelations() {
        return Collections.emptySet();
    }

    @Override
    public ResultFormatter getResultFormatter() {
        //todo: instance formatter
        return getId() == null ? new CollectionFormatter(this) : new ServiceInstanceFormatter(this);
    }
}
