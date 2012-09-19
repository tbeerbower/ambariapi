package org.apache.ambari.api.ganglia;

import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class GangliaHelperTest {


    @Ignore @Test
    public void testGetGangliaMetrics() throws Exception{
        //MM/dd/yy HH:mm:ss

        String target  = "ec2-107-22-86-120.compute-1.amazonaws.com";
        String cluster = "HDPNameNode";
        String host    = "domU-12-31-39-15-25-C7.compute-1.internal";
        Date startTime = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse("09/12/12 10:00:00");
        Date endTime   = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse("09/12/12 16:15:00");
        long step      = 60;
//        String api  = "rpcdetailed.rpcdetailed.sendHeartbeat_num_ops";
        String metric  = "cpu_nice";

        List<GangliaMetric> metrics = GangliaHelper.getGangliaMetrics(target, cluster, host, metric, startTime, endTime, step);

        System.out.println("GangliaHelperTest.testGetMetrics : api = " + metrics);

        //TODO : assertions
    }
}
