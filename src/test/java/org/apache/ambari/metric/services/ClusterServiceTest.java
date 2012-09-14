package org.apache.ambari.metric.services;

import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.ClusterResourceDefinition;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.easymock.EasyMock.*;
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
                eq(new ClusterResourceDefinition(clusterName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        ClusterService clusterService = new TestClusterService(requestFactory, responseFactory, requestHandler);
        assertSame(response, clusterService.getCluster(httpHeaders, uriInfo, clusterName));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetClusters() {
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
                eq(new ClusterResourceDefinition(null)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        ClusterService clusterService = new TestClusterService(requestFactory, responseFactory, requestHandler);
        assertSame(response, clusterService.getClusters(httpHeaders, uriInfo));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    private class TestClusterService extends ClusterService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;

        private TestClusterService(RequestFactory requestFactory, ResponseFactory responseFactory, RequestHandler handler) {
            m_requestFactory = requestFactory;
            m_responseFactory = responseFactory;
            m_requestHandler = handler;
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
