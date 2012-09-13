package org.apache.ambari.metric.jmx;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class JMXHelperTest {

    @Ignore @Test
    public void testGetJMXMetrics() throws Exception{

        String target  = "ec2-107-22-86-120.compute-1.amazonaws.com:50070";

        JMXMetrics metrics = JMXHelper.getJMXMetrics(target, null);

        System.out.println("GangliaHelperTest.testGetMetrics : metric = " + metrics);

        //TODO : assertions
    }
}
