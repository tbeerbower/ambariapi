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
package org.apache.ambari.controller.internal;

import org.apache.ambari.controller.jdbc.ConnectionFactory;
import org.apache.ambari.controller.jdbc.JDBCResourceProvider;
import org.apache.ambari.controller.jdbc.SQLiteConnectionFactory;
import org.apache.ambari.controller.spi.ClusterController;
import org.apache.ambari.controller.spi.Predicate;
import org.apache.ambari.controller.spi.Request;
import org.apache.ambari.controller.spi.Resource;
import org.apache.ambari.controller.spi.ResourceProvider;
import org.apache.ambari.controller.spi.Schema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Default cluster controller implementation.
 */
public class ClusterControllerImpl implements ClusterController {

  private static final ClusterController SINGLETON = new ClusterControllerImpl();

  // TODO inject...
  public final ConnectionFactory CONNECTION_FACTORY = new SQLiteConnectionFactory();

  // TODO inject...
  public final ResourceProvider ClusterResourceProvider = JDBCResourceProvider.create(CONNECTION_FACTORY, Resource.Type.Cluster);
  public final ResourceProvider ServiceResourceProvider = JDBCResourceProvider.create(CONNECTION_FACTORY, Resource.Type.Service);
  public final ResourceProvider HostResourceProvider = JDBCResourceProvider.create(CONNECTION_FACTORY, Resource.Type.Host);
  public final ResourceProvider ComponentResourceProvider = JDBCResourceProvider.create(CONNECTION_FACTORY, Resource.Type.Component);
  public final ResourceProvider HostComponentResourceProvider = JDBCResourceProvider.create(CONNECTION_FACTORY, Resource.Type.HostComponent);

  private final Map<Resource.Type, ResourceProvider> providers = new HashMap<Resource.Type, ResourceProvider>();

  private ClusterControllerImpl() {
    providers.put(Resource.Type.Cluster, ClusterResourceProvider);
    providers.put(Resource.Type.Service, ServiceResourceProvider);
    providers.put(Resource.Type.Host, HostResourceProvider);
    providers.put(Resource.Type.Component, ComponentResourceProvider);
    providers.put(Resource.Type.HostComponent, HostComponentResourceProvider);
  }

  public static ClusterController getSingleton() {
    return SINGLETON;
  }

  @Override
  public Iterable<Resource> getResources(Resource.Type type, Request request, Predicate predicate) {
    ResourceProvider provider = providers.get(type);
    Set<Resource> resources = null;
    if (provider != null) {
      resources = provider.getResources(request, predicate);
    }
    return new ResourceIterable(resources, predicate);
  }

  @Override
  public Schema getSchema(Resource.Type type) {
    ResourceProvider provider = providers.get(type);
    if (provider != null) {
      return provider.getSchema();
    }
    return null;
  }

  private static class ResourceIterable implements Iterable<Resource> {
    private final Set<Resource> resources;
    private final Predicate predicate;

    private ResourceIterable(Set<Resource> resources, Predicate predicate) {
      this.resources = resources;
      this.predicate = predicate;
    }

    @Override
    public Iterator<Resource> iterator() {
      return new ResourceIterator(resources, predicate);
    }
  }

  private static class ResourceIterator implements Iterator<Resource> {

    private final Iterator<Resource> iterator;
    private final Predicate predicate;
    private Resource nextResource;

    private ResourceIterator(Set<Resource> resources, Predicate predicate) {
      this.iterator = resources.iterator();
      this.predicate = predicate;
      this.nextResource = getNextResource();
    }

    @Override
    public boolean hasNext() {
      return nextResource != null;
    }

    @Override
    public Resource next() {
      if (nextResource == null) {
        throw new NoSuchElementException("Iterator has no more elements.");
      }

      Resource currentResource = nextResource;
      this.nextResource = getNextResource();

      return currentResource;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Remove not supported.");
    }

    private Resource getNextResource() {
      while (iterator.hasNext()) {
        Resource next = iterator.next();
        if (predicate == null || predicate.evaluate(next)) {
          return next;
        }
      }
      return null;
    }
  }
}
