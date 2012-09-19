package org.apache.ambari.api.handlers;

import org.apache.ambari.api.query.Query;
import org.apache.ambari.api.resource.ResourceDefinition;
import org.apache.ambari.api.services.Request;
import org.apache.ambari.api.services.Result;
import org.junit.Test;


import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/12/12
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadRequestHandlerTest {

    @Test
    public void testHandlerRequest() {
        Request request = createStrictMock(Request.class);
        ResourceDefinition resourceDefinition = createStrictMock(ResourceDefinition.class);
        Query query = createStrictMock(Query.class);
        Result result = createStrictMock(Result.class);

        //expectations
        expect(request.getResource()).andReturn(resourceDefinition);
        expect(resourceDefinition.getQuery()).andReturn(query);
        expect(query.execute()).andReturn(result);

        replay(request, resourceDefinition, query, result);

        //test
        ReadRequestHandler handler = new ReadRequestHandler();
        assertSame(result, handler.handleRequest(request));

        verify(request, resourceDefinition, query, result);

    }
}
