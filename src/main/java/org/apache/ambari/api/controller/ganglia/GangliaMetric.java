/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.api.controller.ganglia;


/**
 *
 */
public class GangliaMetric {

  private String ds_name;
  private String cluster_name;
  private String graph_type;
  private String host_name;
  private String metric_name;

  private double[][] datapoints;

  public String getDs_name() {
    return ds_name;
  }

  public void setDs_name(String ds_name) {
    this.ds_name = ds_name;
  }

  public String getCluster_name() {
    return cluster_name;
  }

  public void setCluster_name(String cluster_name) {
    this.cluster_name = cluster_name;
  }

  public String getGraph_type() {
    return graph_type;
  }

  public void setGraph_type(String graph_type) {
    this.graph_type = graph_type;
  }

  public String getHost_name() {
    return host_name;
  }

  public void setHost_name(String host_name) {
    this.host_name = host_name;
  }

  public String getMetric_name() {
    return metric_name;
  }

  public void setMetric_name(String metric_name) {
    this.metric_name = metric_name;
  }

  public double[][] getDatapoints() {
    return datapoints;
  }

  public void setDatapoints(double[][] datapoints) {
    this.datapoints = datapoints;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\n");
    stringBuilder.append("name=");
    stringBuilder.append(ds_name);
    stringBuilder.append("\n");
    stringBuilder.append("cluster name=");
    stringBuilder.append(cluster_name);
    stringBuilder.append("\n");
    stringBuilder.append("graph type=");
    stringBuilder.append(graph_type);
    stringBuilder.append("\n");
    stringBuilder.append("host name=");
    stringBuilder.append(host_name);
    stringBuilder.append("\n");
    stringBuilder.append("api name=");
    stringBuilder.append(metric_name);
    stringBuilder.append("\n");

    stringBuilder.append("datapoints (value/timestamp):");
    stringBuilder.append("\n");


    boolean first = true;
    stringBuilder.append("[");
    for (double[] m : datapoints) {
      if (!first) {
        stringBuilder.append(",");
      }
      stringBuilder.append("[");
      stringBuilder.append(m[0]);
      stringBuilder.append(",");
      stringBuilder.append((long) m[1]);
      stringBuilder.append("]");
      first = false;
    }
    stringBuilder.append("]");

    return stringBuilder.toString();
  }
}
