package org.apache.ambari.api.handlers;

import org.apache.ambari.api.services.Request;
import org.apache.ambari.api.services.Result;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatingRequestHandlerTest {

    @Test
    public void testHandleRequest_GET() {
        Request request = createStrictMock(Request.class);
        RequestHandlerFactory factory = createStrictMock(RequestHandlerFactory.class);
        RequestHandler readRequestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);

        // expectations
        expect(request.getRequestType()).andReturn(Request.RequestType.GET);
        expect(factory.getRequestHandler(Request.RequestType.GET)).andReturn(readRequestHandler);
        expect(readRequestHandler.handleRequest(request)).andReturn(result);

        replay(request, factory, readRequestHandler, result);

        RequestHandler delegatingRequestHandler = new TestDelegatingRequestHandler(factory);

        assertSame(result, delegatingRequestHandler.handleRequest(request));
        verify(request, factory, readRequestHandler, result);
    }

    @Test
    public void testHandleRequest_PUT() {
        Request request = createStrictMock(Request.class);
        RequestHandlerFactory factory = createStrictMock(RequestHandlerFactory.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);

        // expectations
        expect(request.getRequestType()).andReturn(Request.RequestType.PUT);
        expect(factory.getRequestHandler(Request.RequestType.PUT)).andReturn(requestHandler);
        expect(requestHandler.handleRequest(request)).andReturn(result);

        replay(request, factory, requestHandler, result);

        RequestHandler delegatingRequestHandler = new TestDelegatingRequestHandler(factory);

        assertSame(result, delegatingRequestHandler.handleRequest(request));
        verify(request, factory, requestHandler, result);
    }

    @Test
    public void testHandleRequest_POST() {
        Request request = createStrictMock(Request.class);
        RequestHandlerFactory factory = createStrictMock(RequestHandlerFactory.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);

        // expectations
        expect(request.getRequestType()).andReturn(Request.RequestType.POST);
        expect(factory.getRequestHandler(Request.RequestType.POST)).andReturn(requestHandler);
        expect(requestHandler.handleRequest(request)).andReturn(result);

        replay(request, factory, requestHandler, result);

        RequestHandler delegatingRequestHandler = new TestDelegatingRequestHandler(factory);

        assertSame(result, delegatingRequestHandler.handleRequest(request));
        verify(request, factory, requestHandler, result);
    }

    @Test
    public void testHandleRequest_DELETE() {
        Request request = createStrictMock(Request.class);
        RequestHandlerFactory factory = createStrictMock(RequestHandlerFactory.class);
        RequestHandler requestHandler = createStrictMock(RequestHandler.class);
        Result result = createStrictMock(Result.class);

        // expectations
        expect(request.getRequestType()).andReturn(Request.RequestType.DELETE);
        expect(factory.getRequestHandler(Request.RequestType.DELETE)).andReturn(requestHandler);
        expect(requestHandler.handleRequest(request)).andReturn(result);

        replay(request, factory, requestHandler, result);

        RequestHandler delegatingRequestHandler = new TestDelegatingRequestHandler(factory);

        assertSame(result, delegatingRequestHandler.handleRequest(request));
        verify(request, factory, requestHandler, result);
    }

    private class TestDelegatingRequestHandler extends DelegatingRequestHandler {
        private RequestHandlerFactory m_factory;

        private TestDelegatingRequestHandler(RequestHandlerFactory factory) {
            m_factory = factory;
        }

        @Override
        public RequestHandlerFactory getRequestHandlerFactory() {
            return m_factory;
        }
    }
}
