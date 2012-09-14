package org.apache.ambari.metric.services;

import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.ServiceResourceDefinition;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceServiceTest {

    @Test
    public void testGetService() {
        RequestFactory requestFactory = createStrictMock(RequestFactory.class);
        ResponseFactory responseFactory = createStrictMock(ResponseFactory.class);
        Request request = createNiceMock(Request.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);
        Response response = createStrictMock(Response.class);

        HttpHeaders httpHeaders = createNiceMock(HttpHeaders.class);
        UriInfo uriInfo = createNiceMock(UriInfo.class);

        String clusterName = "clusterName";
        String serviceName = "hostName";

        // expectations
        expect(requestFactory.createRequest(eq(httpHeaders), eq(uriInfo), eq(Request.RequestType.GET),
                eq(new ServiceResourceDefinition(serviceName, clusterName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        ServiceService hostService = new TestServiceService(clusterName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getService(httpHeaders, uriInfo, serviceName));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetServices() {
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
                eq(new ServiceResourceDefinition(null, clusterName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        ServiceService hostService = new TestServiceService(clusterName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getServices(httpHeaders, uriInfo));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    private class TestServiceService extends ServiceService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;

        private TestServiceService(String clusterId, RequestFactory requestFactory, ResponseFactory responseFactory, RequestHandler handler) {
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
