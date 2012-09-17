package org.apache.ambari.metric.query;

import org.apache.ambari.metric.internal.ClusterControllerImpl;
import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.internal.RequestImpl;
import org.apache.ambari.metric.predicate.AndPredicate;
import org.apache.ambari.metric.predicate.BasePredicate;
import org.apache.ambari.metric.predicate.EqualsPredicate;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.services.ResultImpl;
import org.apache.ambari.metric.spi.*;
import org.apache.ambari.metric.resource.ResourceDefinition;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryImpl implements Query {
    ResourceDefinition m_resourceDefinition;
    Predicate m_predicate;
    private Map<String, Set<String>> m_mapProperties = new HashMap<String, Set<String>>();
    private Map<ResourceDefinition, Query> m_mapSubQueries = new HashMap<ResourceDefinition, Query>();



    public QueryImpl(ResourceDefinition resourceDefinition) {
        m_resourceDefinition = resourceDefinition;
    }

    @Override
    public Result execute() {
        initialize();

        Result result = createResult();
        Iterable<Resource> iterResource = getClusterController().getResources(
                m_resourceDefinition.getType(), createRequest(), m_predicate);

        List<Resource> listResources = new ArrayList<Resource>();
        for (Resource resource : iterResource) {
            listResources.add(resource);
        }
        //todo: tree?
        result.addResources("/", listResources);

        for (Map.Entry<ResourceDefinition, Query> entry : m_mapSubQueries.entrySet()) {
            Query query = entry.getValue();
            ResourceDefinition resDef = entry.getKey();

            //todo: this ensures that the sub query is only executed if needed.  Refactor.
            if (m_mapProperties.isEmpty() || m_mapProperties.containsKey(resDef.getId() == null ?
                    resDef.getPluralName() : resDef.getSingularName())) {
                Map<String, List<Resource>> mapSubResults = query.execute().getResources();
                //todo: only getting sub-resource one level deep at this time
                List<Resource> listSubResources = mapSubResults.get("/");
                String subResourceName = resDef.getId() == null ? resDef.getPluralName() : resDef.getSingularName();
                result.addResources(subResourceName, listSubResources);
            }
        }

        return result;
    }

    //todo: refactor
    public void initialize() {
        m_predicate = createPredicate(m_resourceDefinition);

        if (m_resourceDefinition.getId() != null) {
            //sub-resource queries
            for (ResourceDefinition resource : m_resourceDefinition.getChildren()) {
                m_mapSubQueries.put(resource, resource.getQuery());
            }
            for (ResourceDefinition resource : m_resourceDefinition.getRelatedResources()) {
                m_mapSubQueries.put(resource, resource.getQuery());
            }
        }
    }

    @Override
    public void addAllProperties(Map<String, Set<String>> mapProperties) {
        m_mapProperties.putAll(mapProperties);
    }

    @Override
    public void addProperty(String path, String property) {
        Set<String> setProps = m_mapProperties.get(path);
        if (setProps == null) {
            setProps = new HashSet<String>();
            m_mapProperties.put(path, setProps);
        }
        setProps.add(property);
    }

    @Override
    public void addProperty(PropertyId property) {
        addProperty(property.getCategory(), property.getName());
    }

    @Override
    public void retainAllProperties(Set<String> setFields) {
        //todo
    }

    @Override
    public void clearAllProperties() {
        m_mapProperties.clear();
    }

    private Predicate createPredicate(ResourceDefinition resourceDefinition) {
        //todo: account for user predicates
        Resource.Type resourceType = resourceDefinition.getType();
        Map<Resource.Type, String> mapResourceIds = resourceDefinition.getResourceIds();
        Schema schema = getClusterController().getSchema(resourceType);

        BasePredicate[] predicates = new BasePredicate[mapResourceIds.size()];
        int count = 0;
        for(Map.Entry<Resource.Type, String> entry : mapResourceIds.entrySet()) {
            predicates[count++] = new EqualsPredicate(schema.getKeyPropertyId(entry.getKey()), entry.getValue());
        }

        if (predicates.length == 1) {
            return predicates[0];
        } else if (predicates.length > 1) {
            return new AndPredicate(predicates);
        } else {
            return null;
        }
    }

    //todo: how to get Controller?
    ClusterController getClusterController() {
        return ClusterControllerImpl.getSingleton();
    }

    //todo
    Request createRequest() {
        Set<PropertyId> setProperties = new HashSet<PropertyId>();
        //todo: convert property names to PropertyId's.
        for (Map.Entry<String, Set<String>> entry : m_mapProperties.entrySet()) {
            String group = entry.getKey();
            for (String property : entry.getValue()) {
                setProperties.add(new PropertyIdImpl(property, group, false));
            }
        }
        return new RequestImpl(setProperties);
    }

    //todo
    Result createResult() {
        return new ResultImpl();
    }


}