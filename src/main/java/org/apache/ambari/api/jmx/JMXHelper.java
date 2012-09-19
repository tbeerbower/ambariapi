package org.apache.ambari.api.jmx;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 */
public class JMXHelper {

    public static JMXMetrics getJMXMetrics(String target, String bean) {
        String s = "http://" + target + "/jmx?qry=" + (bean == null ? "Hadoop:*" : bean );
        try {
            URLConnection connection = new URL(s).openConnection();

            connection.setDoOutput(true);

            return new ObjectMapper().readValue(connection.getInputStream(),
                    JMXMetrics.class);

        } catch (IOException e) {
            System.out.println("getJMXMetrics : caught " + e);
            throw new IllegalStateException("Can't get metric " + ".", e);
        }
    }
}
