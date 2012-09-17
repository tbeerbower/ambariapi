package org.apache.ambari.metric.query;

import org.apache.ambari.metric.internal.PropertyIdImpl;
import org.apache.ambari.metric.predicate.BasePredicate;
import org.apache.ambari.metric.predicate.AndPredicate;
import org.apache.ambari.metric.predicate.EqualsPredicate;
import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.Result;
import org.apache.ambari.metric.spi.*;
import org.junit.Test;
import java.util.*;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryImplTest {
    @Test
    public void testExecute__Component_Instance() {
        ResourceDefinition componentResourceDef = createMock(ResourceDefinition.class);
        ResourceDefinition hostComponentResourceDef = createStrictMock(ResourceDefinition.class);
        Request request = createStrictMock(Request.class);
        Result result = createStrictMock(Result.class);
        ClusterController controller = createStrictMock(ClusterController.class);
        Schema componentSchema = createMock(Schema.class);
        Resource componentResource = createStrictMock(Resource.class);
        Query hostComponentQuery = createStrictMock(Query.class);
        Result hostComponentQueryResult  = createStrictMock(Result.class);
        Resource hostComponentResource = createStrictMock(Resource.class);

        List<Resource> listResources = new ArrayList<Resource>();
        listResources.add(componentResource);

        Map<Resource.Type, String> mapResourceIds = new HashMap<Resource.Type, String>();
        mapResourceIds.put(Resource.Type.Cluster, "clusterName");
        mapResourceIds.put(Resource.Type.Service, "serviceName");
        mapResourceIds.put(Resource.Type.Component, "componentName");

        Set<ResourceDefinition> setChildren = new HashSet<ResourceDefinition>();
        Set<ResourceDefinition> setForeign = new HashSet<ResourceDefinition>();
        setForeign.add(hostComponentResourceDef);

        Map<String, List<Resource>> mapHostComponentResources = new HashMap<String, List<Resource>>();
        mapHostComponentResources.put("/", Collections.singletonList(hostComponentResource));

        // expectations
        expect(componentResourceDef.getType()).andReturn(Resource.Type.Component).atLeastOnce();
        expect(componentResourceDef.getResourceIds()).andReturn(mapResourceIds);
        expect(controller.getSchema(Resource.Type.Component)).andReturn(componentSchema).atLeastOnce();
        expect(componentSchema.getKeyPropertyId(Resource.Type.Cluster)).andReturn(new PropertyIdImpl("clusterId", "", false));
        expect(componentSchema.getKeyPropertyId(Resource.Type.Service)).andReturn(new PropertyIdImpl("serviceId", "", false));
        expect(componentSchema.getKeyPropertyId(Resource.Type.Component)).andReturn(new PropertyIdImpl("componentId", "", false));

        expect(componentResourceDef.getId()).andReturn("componentName").atLeastOnce();
        expect(componentResourceDef.getChildren()).andReturn(setChildren);
        expect(componentResourceDef.getRelatedResources()).andReturn(setForeign);
        expect(hostComponentResourceDef.getQuery()).andReturn(hostComponentQuery);

        EqualsPredicate clusterEqualsPredicate = new EqualsPredicate(new PropertyIdImpl("clusterId", "", false), "clusterName");
        EqualsPredicate serviceEqualsPredicate = new EqualsPredicate(new PropertyIdImpl("serviceId", "", false), "serviceName");
        EqualsPredicate componentEqualsPredicate = new EqualsPredicate(new PropertyIdImpl("componentId", "", false), "componentName");
        BasePredicate[] predicates = new BasePredicate[3];
        predicates[0] = clusterEqualsPredicate;
        predicates[1] = serviceEqualsPredicate;
        predicates[2] = componentEqualsPredicate;
        AndPredicate andPredicate = new AndPredicate(predicates);

        expect(controller.getResources(eq(Resource.Type.Component), eq(request), eq(andPredicate))).
                andReturn(listResources);

        result.addResources("/", listResources);

        expect(hostComponentQuery.execute()).andReturn(hostComponentQueryResult);
        expect(hostComponentQueryResult.getResources()).andReturn(mapHostComponentResources);
        expect(hostComponentResourceDef.getId()).andReturn("hostComponentName");
        expect(hostComponentResourceDef.getSingularName()).andReturn("host_component");
        result.addResources("host_component", Collections.singletonList(hostComponentResource));

        replay(componentResourceDef, request, result, controller, componentSchema, componentResource,
                hostComponentResourceDef, hostComponentQuery, hostComponentQueryResult, hostComponentResource);

        QueryImpl query = new TestQuery(componentResourceDef, result, request, controller);
        Result testResult = query.execute();
        // todo: assert return value.  This is currently a mock.

        verify(componentResourceDef, request, result, controller, componentSchema, componentResource,
                hostComponentResourceDef, hostComponentQuery, hostComponentQueryResult, hostComponentResource);
    }

    @Test
    public void testExecute__Component_Collection() {
        ResourceDefinition componentResourceDef = createMock(ResourceDefinition.class);
        Request request = createStrictMock(Request.class);
        Result result = createStrictMock(Result.class);
        ClusterController controller = createStrictMock(ClusterController.class);
        Schema componentSchema = createMock(Schema.class);
        Resource componentResource1 = createStrictMock(Resource.class);
        Resource componentResource2 = createStrictMock(Resource.class);

        List<Resource> listResources = new ArrayList<Resource>();
        listResources.add(componentResource1);
        listResources.add(componentResource2);

        Map<Resource.Type, String> mapResourceIds = new HashMap<Resource.Type, String>();
        mapResourceIds.put(Resource.Type.Cluster, "clusterName");
        mapResourceIds.put(Resource.Type.Service, "serviceName");

        // expectations
        expect(componentResourceDef.getType()).andReturn(Resource.Type.Component).atLeastOnce();
        expect(componentResourceDef.getResourceIds()).andReturn(mapResourceIds);
        expect(controller.getSchema(Resource.Type.Component)).andReturn(componentSchema).atLeastOnce();
        expect(componentSchema.getKeyPropertyId(Resource.Type.Cluster)).andReturn(new PropertyIdImpl("clusterId", "", false));
        expect(componentSchema.getKeyPropertyId(Resource.Type.Service)).andReturn(new PropertyIdImpl("serviceId", "", false));

        expect(componentResourceDef.getId()).andReturn(null).atLeastOnce();

        EqualsPredicate clusterEqualsPredicate = new EqualsPredicate(new PropertyIdImpl("clusterId", "", false), "clusterName");
        EqualsPredicate serviceEqualsPredicate = new EqualsPredicate(new PropertyIdImpl("serviceId", "", false), "serviceName");
        BasePredicate[] predicates = new BasePredicate[2];
        predicates[0] = clusterEqualsPredicate;
        predicates[1] = serviceEqualsPredicate;
        Predicate andPredicate = new AndPredicate(predicates);

        expect(controller.getResources(eq(Resource.Type.Component), eq(request), eq(andPredicate))).
                andReturn(listResources);

        result.addResources("/", listResources);

        replay(componentResourceDef, request, result, controller,componentSchema, componentResource1, componentResource2);

        QueryImpl query = new TestQuery(componentResourceDef, result, request, controller);
        Result testResult = query.execute();
        // todo: assert return value.  This is currently a mock.

        verify(componentResourceDef, request, result, controller,componentSchema, componentResource1, componentResource2);
    }

    @Test
    public void testExecute__Cluster_Instance() {
        ResourceDefinition clusterResourceDef = createMock(ResourceDefinition.class);
        ResourceDefinition serviceResourceDef = createMock(ResourceDefinition.class);
        ResourceDefinition hostResourceDef = createMock(ResourceDefinition.class);
        Request request = createStrictMock(Request.class);
        Result result = createMock(Result.class);
        ClusterController controller = createStrictMock(ClusterController.class);
        Schema clusterSchema = createMock(Schema.class);
        Resource clusterResource = createStrictMock(Resource.class);
        Query serviceQuery = createStrictMock(Query.class);
        Result serviceQueryResult  = createStrictMock(Result.class);
        Resource serviceResource = createStrictMock(Resource.class);
        Resource serviceResource2 = createStrictMock(Resource.class);
        Query hostQuery = createStrictMock(Query.class);
        Result hostQueryResult  = createStrictMock(Result.class);
        Resource hostResource = createStrictMock(Resource.class);

        List<Resource> listResources = new ArrayList<Resource>();
        listResources.add(clusterResource);

        Map<Resource.Type, String> mapResourceIds = new HashMap<Resource.Type, String>();
        mapResourceIds.put(Resource.Type.Cluster, "clusterName");

        Set<ResourceDefinition> setChildren = new HashSet<ResourceDefinition>();
        setChildren.add(serviceResourceDef);
        setChildren.add(hostResourceDef);
        Set<ResourceDefinition> setForeign = new HashSet<ResourceDefinition>();

        Map<String, List<Resource>> mapServiceResources = new HashMap<String, List<Resource>>();
        List<Resource> listServiceResources = new ArrayList<Resource>();
        listServiceResources.add(serviceResource);
        listServiceResources.add(serviceResource2);
        mapServiceResources.put("/", listServiceResources);

        Map<String, List<Resource>> mapHostResources = new HashMap<String, List<Resource>>();
        mapHostResources.put("/", Collections.singletonList(hostResource));

        // expectations
        expect(clusterResourceDef.getType()).andReturn(Resource.Type.Cluster).atLeastOnce();
        expect(clusterResourceDef.getResourceIds()).andReturn(mapResourceIds);
        expect(controller.getSchema(Resource.Type.Cluster)).andReturn(clusterSchema).atLeastOnce();
        expect(clusterSchema.getKeyPropertyId(Resource.Type.Cluster)).andReturn(new PropertyIdImpl("clusterId", "", false));
        expect(clusterResourceDef.getId()).andReturn("clusterName").atLeastOnce();

        expect(clusterResourceDef.getChildren()).andReturn(setChildren);
        expect(serviceResourceDef.getQuery()).andReturn(serviceQuery);
        expect(hostResourceDef.getQuery()).andReturn(hostQuery);
        expect(clusterResourceDef.getRelatedResources()).andReturn(setForeign);

        Predicate clusterEqualsPredicate = new EqualsPredicate(new PropertyIdImpl("clusterId", "", false), "clusterName");

        expect(controller.getResources(eq(Resource.Type.Cluster), eq(request), eq(clusterEqualsPredicate))).
                andReturn(listResources);

        result.addResources("/", listResources);

        expect(serviceQuery.execute()).andReturn(serviceQueryResult);
        expect(serviceQueryResult.getResources()).andReturn(mapServiceResources);
        expect(serviceResourceDef.getId()).andReturn(null);
        expect(serviceResourceDef.getPluralName()).andReturn("services");
        result.addResources("services",listServiceResources);

        expect(hostQuery.execute()).andReturn(hostQueryResult);
        expect(hostQueryResult.getResources()).andReturn(mapHostResources);
        expect(hostResourceDef.getId()).andReturn(null);
        expect(hostResourceDef.getPluralName()).andReturn("hosts");
        result.addResources("hosts", Collections.singletonList(hostResource));

        replay(clusterResourceDef, request, result, controller, clusterSchema, clusterResource,
                serviceResourceDef, serviceQuery, serviceQueryResult, serviceResource, serviceResource2,
                hostResourceDef, hostQuery, hostQueryResult, hostResource);

        QueryImpl query = new TestQuery(clusterResourceDef, result, request, controller);
        Result testResult = query.execute();
        // todo: assert return value.  This is currently a mock.

        verify(clusterResourceDef, request, result, controller, clusterSchema, clusterResource,
                serviceResourceDef, serviceQuery, serviceQueryResult, serviceResource, serviceResource2,
                hostResourceDef, hostQuery, hostQueryResult, hostResource);
    }

    private class TestQuery extends QueryImpl {

        private Result m_result;
        private Request m_request;
        private ClusterController m_clusterController;

        public TestQuery(ResourceDefinition resourceDefinition, Result result, Request request, ClusterController controller) {
            super(resourceDefinition);
            m_result = result;
            m_request = request;
            m_clusterController = controller;
        }

        @Override
        Result createResult() {
            return m_result;
        }

        @Override
        Request createRequest() {
            return m_request;
        }

        @Override
        ClusterController getClusterController() {
            return m_clusterController;
        }
    }
}