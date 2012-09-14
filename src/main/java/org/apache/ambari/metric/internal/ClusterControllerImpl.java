package org.apache.ambari.metric.internal;

import org.apache.ambari.metric.jdbc.SQLiteResourceProvider;
import org.apache.ambari.metric.spi.ClusterController;
import org.apache.ambari.metric.spi.Predicate;
import org.apache.ambari.metric.spi.Request;
import org.apache.ambari.metric.spi.Resource;
import org.apache.ambari.metric.spi.ResourceProvider;
import org.apache.ambari.metric.spi.Schema;
import org.apache.ambari.metric.utilities.Properties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Default cluster controller implementation.
 */
public class ClusterControllerImpl implements ClusterController{

    private static final ClusterController SINGLETON = new ClusterControllerImpl();

    public static final String CONNECTION_URL = "jdbc:sqlite:src/test/resources/data.db";

    private final Map<Resource.Type, ResourceProvider> providers = new HashMap<Resource.Type, ResourceProvider>();


    public static ClusterController getSingleton() {
        return SINGLETON;
    }

    private ClusterControllerImpl() {
        createResourceProvider(Resource.Type.Cluster);
        createResourceProvider(Resource.Type.Service);
        createResourceProvider(Resource.Type.Host);
        createResourceProvider(Resource.Type.Component);
        createResourceProvider(Resource.Type.HostComponent);
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

    private void createResourceProvider(Resource.Type type) {
        ResourceProvider provider = new SQLiteResourceProvider(CONNECTION_URL, type,
                Properties.getPropertyIds(type, "DB"),
                Properties.getKeyPropertyIds(type));

        providers.put(type, provider);
    }

    private static class ResourceIterable implements Iterable<Resource> {
        private final Set<Resource> resources;
        private final Predicate     predicate;

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
        private final Predicate          predicate;
        private       Resource           nextResource;

        private ResourceIterator(Set<Resource> resources, Predicate predicate) {
            this.iterator     = resources.iterator();
            this.predicate    = predicate;
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
            this.nextResource        = getNextResource();

            return currentResource;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }

        private Resource getNextResource() {
            while (iterator.hasNext()) {
                Resource next = iterator.next();
                if (predicate == null || predicate.apply(next)) {
                    return next;
                }
            }
            return null;
        }
    }
}