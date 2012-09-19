package org.apache.ambari.api.jmx;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class JMXMetrics {

    private List<Map<String, String>> beans;

    public List<Map<String, String>> getBeans() {
        return beans;
    }

    public void setBeans(List<Map<String, String>> beans) {
        this.beans = beans;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map<String, String> map : beans) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuilder.append("    " + entry.toString() + "\n");
            }
        }
        return stringBuilder.toString();
    }
}
