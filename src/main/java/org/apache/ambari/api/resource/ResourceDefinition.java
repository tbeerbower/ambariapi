package org.apache.ambari.api.resource;

import org.apache.ambari.api.query.Query;
import org.apache.ambari.api.services.formatters.ResultFormatter;
import org.apache.ambari.controller.spi.Resource;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceDefinition {
    public String getPluralName();
    public String getSingularName();
    public String getId();
    public Set<ResourceDefinition> getChildren();
    public Set<ResourceDefinition> getRelations();
    public Map<Resource.Type, String> getResourceIds();
    public ResultFormatter getResultFormatter();

    public Resource.Type getType();
    public Query getQuery();
}
