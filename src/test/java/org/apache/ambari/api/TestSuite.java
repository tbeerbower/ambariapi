package org.apache.ambari.api;

/**
 * All unit tests.
 */
import org.apache.ambari.api.handlers.DelegatingRequestHandlerTest;
import org.apache.ambari.api.handlers.ReadRequestHandlerTest;
import org.apache.ambari.api.query.QueryImplTest;
import org.apache.ambari.api.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ClusterServiceTest.class, HostServiceTest.class, ServiceServiceTest.class,
        ComponentServiceTest.class, HostComponentServiceTest.class, DelegatingRequestHandlerTest.class,
        ReadRequestHandlerTest.class, QueryImplTest.class})
public class TestSuite {
}
