package org.apache.ambari.api.services;

import org.apache.ambari.api.handlers.RequestHandler;
import org.apache.ambari.api.resource.ResourceDefinition;
import org.apache.ambari.api.services.formatters.ResultFormatter;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 6/21/12
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterServiceTest {

    @Test
    public void testGetCluster() {
        ResourceDefinition resourceDef = createStrictMock(ResourceDefinition.class);
        ResultFormatter resultFormatter = createStrictMock(ResultFormatter.class);
        Object formattedResult = new Object();
        Serializer serializer = createStrictMock(Serializer.class);
        Object serializedResult = new Object();
        RequestFactory requestFactory = createStrictMock(RequestFactory.class);
        ResponseFactory responseFactory = createStrictMock(ResponseFactory.class);
        Request request = createNiceMock(Request.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);
        Response response = createStrictMock(Response.class);

        HttpHeaders httpHeaders = createNiceMock(HttpHeaders.class);
        UriInfo uriInfo = createNiceMock(UriInfo.class);

        String clusterName = "clusterName";

        // expectations
        expect(requestFactory.createRequest(eq(httpHeaders), eq(uriInfo), eq(Request.RequestType.GET),
                eq(resourceDef))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(resourceDef.getResultFormatter()).andReturn(resultFormatter);
        expect(resultFormatter.format(result, uriInfo)).andReturn(formattedResult);
        expect(request.getSerializer()).andReturn(serializer);
        expect(serializer.serialize(formattedResult)).andReturn(serializedResult);

        expect(responseFactory.createResponse(serializedResult)).andReturn(response);

        replay(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request,requestHandler,
                result, response, httpHeaders, uriInfo);

        //test
        ClusterService clusterService = new TestClusterService(resourceDef, clusterName, requestFactory, responseFactory, requestHandler);
        assertSame(response, clusterService.getCluster(httpHeaders, uriInfo, clusterName));

        verify(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request, requestHandler,
                result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetClusters() {
        ResourceDefinition resourceDef = createStrictMock(ResourceDefinition.class);
        ResultFormatter resultFormatter = createStrictMock(ResultFormatter.class);
        Object formattedResult = new Object();
        Serializer serializer = createStrictMock(Serializer.class);
        Object serializedResult = new Object();
        RequestFactory requestFactory = createStrictMock(RequestFactory.class);
        ResponseFactory responseFactory = createStrictMock(ResponseFactory.class);
        Request request = createNiceMock(Request.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);
        Response response = createStrictMock(Response.class);

        HttpHeaders httpHeaders = createNiceMock(HttpHeaders.class);
        UriInfo uriInfo = createNiceMock(UriInfo.class);

        // expectations
        expect(requestFactory.createRequest(eq(httpHeaders), eq(uriInfo), eq(Request.RequestType.GET),
                eq(resourceDef))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(resourceDef.getResultFormatter()).andReturn(resultFormatter);
        expect(resultFormatter.format(result, uriInfo)).andReturn(formattedResult);
        expect(request.getSerializer()).andReturn(serializer);
        expect(serializer.serialize(formattedResult)).andReturn(serializedResult);

        expect(responseFactory.createResponse(serializedResult)).andReturn(response);

        replay(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request,requestHandler,
                result, response, httpHeaders, uriInfo);

        //test
        ClusterService clusterService = new TestClusterService(resourceDef, null, requestFactory, responseFactory, requestHandler);
        assertSame(response, clusterService.getClusters(httpHeaders, uriInfo));

        verify(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request,requestHandler,
                result, response, httpHeaders, uriInfo);
    }

    private class TestClusterService extends ClusterService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;
        private ResourceDefinition m_resourceDef;
        private String m_clusterId;

        private TestClusterService(ResourceDefinition resourceDef, String clusterId, RequestFactory requestFactory,
                                   ResponseFactory responseFactory, RequestHandler handler) {
            m_resourceDef = resourceDef;
            m_requestFactory = requestFactory;
            m_responseFactory = responseFactory;
            m_requestHandler = handler;
            m_clusterId = clusterId;
        }

        @Override
        ResourceDefinition createResourceDefinition(String clusterName) {
            assertEquals(m_clusterId, clusterName);
            return m_resourceDef;
        }

        @Override
        RequestFactory getRequestFactory() {
            return m_requestFactory;
        }

        @Override
        ResponseFactory getResponseFactory() {
            return m_responseFactory;
        }

        @Override
        RequestHandler getRequestHandler() {
            return m_requestHandler;
        }
    }

    //todo: test getHostHandler, getServiceHandler
}