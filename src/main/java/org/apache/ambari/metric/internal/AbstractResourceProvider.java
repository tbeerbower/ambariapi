package org.apache.ambari.metric.internal;

import org.apache.ambari.metric.spi.PropertyId;
import org.apache.ambari.metric.spi.PropertyProvider;
import org.apache.ambari.metric.spi.ResourceProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract resource provider implementation.
 */
public abstract class AbstractResourceProvider implements ResourceProvider{
    private final List<PropertyProvider> propertyProviders = new LinkedList<PropertyProvider>();

    @Override
    public void addPropertyProvider(PropertyProvider provider) {
        propertyProviders.add(provider);
    }

    protected List<PropertyProvider> getPropertyProviders() {
        return propertyProviders;
    }
}
