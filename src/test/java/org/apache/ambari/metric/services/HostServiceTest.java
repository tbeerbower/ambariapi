package org.apache.ambari.metric.services;


import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.HostResourceDefinition;
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
public class HostServiceTest {

    @Test
    public void testGetHost() {
        RequestFactory requestFactory = createStrictMock(RequestFactory.class);
        ResponseFactory responseFactory = createStrictMock(ResponseFactory.class);
        Request request = createNiceMock(Request.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);
        Response response = createStrictMock(Response.class);

        HttpHeaders httpHeaders = createNiceMock(HttpHeaders.class);
        UriInfo uriInfo = createNiceMock(UriInfo.class);

        String clusterName = "clusterName";
        String hostName = "hostName";

        // expectations
        expect(requestFactory.createRequest(eq(httpHeaders), eq(uriInfo), eq(Request.RequestType.GET),
                eq(new HostResourceDefinition(hostName, clusterName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        HostService hostService = new TestHostService(clusterName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getHost(httpHeaders, uriInfo, hostName));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetHosts() {
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
                eq(new HostResourceDefinition(null, clusterName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        HostService hostService = new TestHostService(clusterName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getHosts(httpHeaders, uriInfo));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    private class TestHostService extends HostService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;

        private TestHostService(String clusterId, RequestFactory requestFactory, ResponseFactory responseFactory, RequestHandler handler) {
            super(clusterId);
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
}


