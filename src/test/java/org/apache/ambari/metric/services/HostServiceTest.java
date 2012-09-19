package org.apache.ambari.metric.services;


import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.HostResourceDefinition;
import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.services.formatters.ResultFormatter;
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
public class HostServiceTest {

    @Test
    public void testGetHost() {
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
        String hostName = "hostName";

        // expectations
        expect(requestFactory.createRequest(eq(httpHeaders), eq(uriInfo), eq(Request.RequestType.GET),
                eq(resourceDef))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(resourceDef.getResultFormatter()).andReturn(resultFormatter);
        expect(resultFormatter.format(result, uriInfo)).andReturn(formattedResult);
        expect(request.getSerializer()).andReturn(serializer);
        expect(serializer.serialize(formattedResult)).andReturn(serializedResult);

        expect(responseFactory.createResponse(serializedResult)).andReturn(response);

        replay(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request, requestHandler,
                result, response, httpHeaders, uriInfo);

        //test
        HostService hostService = new TestHostService(resourceDef, clusterName, hostName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getHost(httpHeaders, uriInfo, hostName));

        verify(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request, requestHandler,
                result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetHosts() {
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

        replay(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request, requestHandler,
                result, response, httpHeaders, uriInfo);

        //test
        HostService hostService = new TestHostService(resourceDef, clusterName, null, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getHosts(httpHeaders, uriInfo));

        verify(resourceDef, resultFormatter, serializer, requestFactory, responseFactory, request, requestHandler,
                result, response, httpHeaders, uriInfo);
    }

    private class TestHostService extends HostService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;
        private ResourceDefinition m_resourceDef;
        private String m_clusterId;
        private String m_hostId;

        private TestHostService(ResourceDefinition resourceDef, String clusterId, String hostId, RequestFactory requestFactory,
                                ResponseFactory responseFactory, RequestHandler handler) {
            super(clusterId);
            m_resourceDef = resourceDef;
            m_clusterId = clusterId;
            m_hostId = hostId;
            m_requestFactory = requestFactory;
            m_responseFactory = responseFactory;
            m_requestHandler = handler;
        }

        @Override
        ResourceDefinition createResourceDefinition(String hostName, String clusterName) {
            assertEquals(m_clusterId, clusterName);
            assertEquals(m_hostId, hostName);
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
}


