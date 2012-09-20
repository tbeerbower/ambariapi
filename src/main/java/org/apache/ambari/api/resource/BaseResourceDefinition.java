package org.apache.ambari.api.resource;


import org.apache.ambari.controller.internal.ClusterControllerImpl;
import org.apache.ambari.api.query.Query;
import org.apache.ambari.api.query.QueryImpl;
import org.apache.ambari.controller.spi.ClusterController;
import org.apache.ambari.controller.spi.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 6/21/12
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseResourceDefinition implements ResourceDefinition {

    private Resource.Type m_type;
    private String m_id;
    private Query m_query = new QueryImpl(this);
    Map<Resource.Type, String> m_mapResourceIds = new HashMap<Resource.Type, String>();

    public BaseResourceDefinition(Resource.Type resourceType, String id) {
        m_type = resourceType;
        m_id = id;

        if (id != null) {
            setResourceId(resourceType, id);
        }
    }

    @Override
    public String getId() {
        return m_id;
    }

    @Override
    public Resource.Type getType() {
        return m_type;
    }


    @Override
    public Query getQuery() {
        return m_query;
    }

    protected void setResourceId(Resource.Type resourceType, String val) {
        //todo: hack for case where service id is null when getting a component from hostComponent
        if (val != null) {
            m_mapResourceIds.put(resourceType, val);
        }
    }

    @Override
    public Map<Resource.Type, String> getResourceIds() {
        return m_mapResourceIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseResourceDefinition)) return false;

        BaseResourceDefinition that = (BaseResourceDefinition) o;

        if (m_id != null ? !m_id.equals(that.m_id) : that.m_id != null) return false;
        if (m_mapResourceIds != null ? !m_mapResourceIds.equals(that.m_mapResourceIds) : that.m_mapResourceIds != null)
            return false;
        if (m_type != that.m_type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = m_type != null ? m_type.hashCode() : 0;
        result = 31 * result + (m_id != null ? m_id.hashCode() : 0);
        result = 31 * result + (m_mapResourceIds != null ? m_mapResourceIds.hashCode() : 0);
        return result;
    }

    ClusterController getClusterController() {
        return ClusterControllerImpl.getSingleton();
    }
}
