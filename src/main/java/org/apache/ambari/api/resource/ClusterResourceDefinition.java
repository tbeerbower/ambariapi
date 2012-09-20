package org.apache.ambari.api.resource;

import org.apache.ambari.api.services.formatters.ClusterInstanceFormatter;
import org.apache.ambari.api.services.formatters.CollectionFormatter;
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
 * Time: 8:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterResourceDefinition extends BaseResourceDefinition {

    public ClusterResourceDefinition(String id) {
        super(Resource.Type.Cluster, id);

        if (id == null) {
            getQuery().addProperty(getClusterController().getSchema(
                    Resource.Type.Cluster).getKeyPropertyId(Resource.Type.Cluster));
        }
    }

    @Override
    public String getPluralName() {
        return "clusters";
    }

    @Override
    public String getSingularName() {
        return "cluster";
    }

    @Override
    public Set<ResourceDefinition> getChildren() {
        Set<ResourceDefinition> setChildren = new HashSet<ResourceDefinition>();

        ServiceResourceDefinition serviceResource = new ServiceResourceDefinition(null, getId());
        PropertyId serviceIdProperty = getClusterController().getSchema(
                Resource.Type.Service).getKeyPropertyId(Resource.Type.Service);
        serviceResource.getQuery().addProperty(serviceIdProperty);
        setChildren.add(serviceResource);

        HostResourceDefinition hostResource = new HostResourceDefinition(null, getId());
        PropertyId hostIdProperty = getClusterController().getSchema(
                Resource.Type.Host).getKeyPropertyId(Resource.Type.Host);
        hostResource.getQuery().addProperty(hostIdProperty);
        setChildren.add(hostResource);

        return setChildren;
    }

    @Override
    public Set<ResourceDefinition> getRelations() {
        return Collections.emptySet();
    }

    @Override
    public ResultFormatter getResultFormatter() {
        //todo: instance formatter
        return getId() == null ? new CollectionFormatter(this) : new ClusterInstanceFormatter(this);
    }
}
