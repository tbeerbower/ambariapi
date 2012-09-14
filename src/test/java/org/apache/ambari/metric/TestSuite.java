package org.apache.ambari.metric;

/**
 * All unit tests.
 */
import org.apache.ambari.metric.handlers.DelegatingRequestHandlerTest;
import org.apache.ambari.metric.handlers.ReadRequestHandlerTest;
import org.apache.ambari.metric.query.QueryImplTest;
import org.apache.ambari.metric.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ClusterServiceTest.class, HostServiceTest.class, ServiceServiceTest.class,
        ComponentServiceTest.class, HostComponentServiceTest.class, DelegatingRequestHandlerTest.class,
        ReadRequestHandlerTest.class, QueryImplTest.class})
public class TestSuite {
}
