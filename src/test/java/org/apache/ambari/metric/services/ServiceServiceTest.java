package org.apache.ambari.metric.services;

import org.apache.ambari.metric.handlers.RequestHandler;
import org.apache.ambari.metric.resource.ResourceDefinition;
import org.apache.ambari.metric.resource.ServiceResourceDefinition;
import org.apache.ambari.metric.services.formatters.ResultFormatter;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
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
        ResourceDefinition resourceDef = createStrictMock(ResourceDefinition.class);
        ResultFormatter resultFormatter = createStrictMock(ResultFormatter.class);
        Object formattedResult = new Object();
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
                eq(resourceDef))).andReturn(request);

        expect(requestHandler.handleRequest(request)).andReturn(result);
        expect(resourceDef.getResultFormatter()).andReturn(resultFormatter);
        expect(resultFormatter.format(result, uriInfo)).andReturn(formattedResult);

        expect(responseFactory.createResponse(formattedResult)).andReturn(response);

        replay(resourceDef, resultFormatter, requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        ServiceService hostService = new TestServiceService(resourceDef, clusterName, serviceName, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getService(httpHeaders, uriInfo, serviceName));

        verify(resourceDef, resultFormatter, requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    @Test
    public void testGetServices() {
        ResourceDefinition resourceDef = createStrictMock(ResourceDefinition.class);
        ResultFormatter resultFormatter = createStrictMock(ResultFormatter.class);
        Object formattedResult = new Object();
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

        expect(responseFactory.createResponse(formattedResult)).andReturn(response);

        replay(resourceDef, resultFormatter, requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);

        //test
        ServiceService hostService = new TestServiceService(resourceDef, clusterName, null, requestFactory, responseFactory, requestHandler);
        assertSame(response, hostService.getServices(httpHeaders, uriInfo));

        verify(resourceDef, resultFormatter, requestFactory, responseFactory, request,requestHandler, result, response, httpHeaders, uriInfo);
    }

    private class TestServiceService extends ServiceService {
        private RequestFactory m_requestFactory;
        private ResponseFactory m_responseFactory;
        private RequestHandler m_requestHandler;
        private ResourceDefinition m_resourceDef;
        private String m_clusterId;
        private String m_serviceId;

        private TestServiceService(ResourceDefinition resourceDef, String clusterId, String serviceId, RequestFactory requestFactory,
                                   ResponseFactory responseFactory, RequestHandler handler) {
            super(clusterId);
            m_resourceDef = resourceDef;
            m_clusterId = clusterId;
            m_serviceId = serviceId;
            m_requestFactory = requestFactory;
            m_responseFactory = responseFactory;
            m_requestHandler = handler;
        }

        @Override
        ResourceDefinition createResourceDefinition(String serviceName, String clusterName) {
            assertEquals(m_clusterId, clusterName);
            assertEquals(m_serviceId, serviceName);
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
