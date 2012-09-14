package org.apache.ambari.metric.services;

import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.HostComponentResourceDefinition;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.eq;
import static org.junit.Assert.assertSame;
/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class HostComponentServiceTest {
    @Test
    public void testGetHostComponent() {
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
        String hostComponentName = "hostComponentName";

        // expectations
        expect(requestFactory.createRequest(eq(httpHeaders), eq(uriInfo), eq(Request.RequestType.GET),
                eq(new HostComponentResourceDefinition(hostComponentName, clusterName, hostName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request, requestHandler, result, response, httpHeaders, uriInfo);

        //test
        HostComponentService hostComponentService = new TestHostComponentService(clusterName, hostName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostComponentService.getHostComponent(httpHeaders, uriInfo, hostComponentName));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetHostComponents() {
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
                eq(new HostComponentResourceDefinition(null, clusterName, hostName)))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(responseFactory.createResponse(result)).andReturn(response);

        replay(requestFactory, responseFactory, request, requestHandler, result, response, httpHeaders, uriInfo);

        //test
        HostComponentService componentService = new TestHostComponentService(clusterName, hostName, requestFactory, responseFactory, requestHandler);
        assertSame(response, componentService.getHostComponents(httpHeaders, uriInfo));

        verify(requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    private class TestHostComponentService extends HostComponentService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;

        private TestHostComponentService(String clusterId, String hostName, RequestFactory requestFactory, ResponseFactory responseFactory, RequestHandler handler) {
            super(clusterId, hostName);
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
