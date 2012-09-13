package org.apache.ambari.metric.ganglia;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 */
public class GangliaHelper {

    public static List<GangliaMetric> getGangliaProperty(String target,
                                                         String gangliaCluster,
                                                         String host,
                                                         String metric){
        String s = "http://" +
                target +
                "/ganglia/graph.php" +
                "?c=" + gangliaCluster +
                "&h=" + (host == null ? "" : host )+
                "&m=" + metric +
                "&json=1";

//        System.out.println("url=" + s);

        try {
            URLConnection connection = new URL(s).openConnection();

            connection.setDoOutput(true);

            return new ObjectMapper().readValue(connection.getInputStream(),
                    new TypeReference<List<GangliaMetric>>() {
                    });

        } catch (IOException e) {
            throw new IllegalStateException("Can't get metric " + metric + ".", e);
        }
    }


    public static List<GangliaMetric> getGangliaMetrics(String target,
                                                        String gangliaCluster,
                                                        String host,
                                                        String metric,
                                                        Date startTime,
                                                        Date endTime,
                                                        long step){
        String s = "http://" +
                target +
                "/ganglia/graph.php" +
                "?c="    + gangliaCluster +
                "&h="    + (host == null ? "" : host )+
                "&m="    + metric +
                "&cs="   + startTime.getTime() / 1000 +
                "&ce="   + endTime.getTime() / 1000 +
                "&step=" + step +
                "&json=1";

        System.out.println("url=" + s);

        try {
            URLConnection connection = new URL(s).openConnection();

            connection.setDoOutput(true);

            return new ObjectMapper().readValue(connection.getInputStream(),
                    new TypeReference<List<GangliaMetric>>() {
                    });

        } catch (IOException e) {
            throw new IllegalStateException("Can't get metric " + metric + ".", e);
        }
    }
}
