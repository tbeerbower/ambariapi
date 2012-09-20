package org.apache.ambari.controller.internal;

import org.apache.ambari.controller.spi.PropertyProvider;
import org.apache.ambari.controller.spi.ResourceProvider;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract resource provider implementation.
 */
public abstract class AbstractResourceProvider implements ResourceProvider{
    private final List<PropertyProvider> propertyProviders = new LinkedList<PropertyProvider>();

    public void addPropertyProvider(PropertyProvider provider) {
        propertyProviders.add(provider);
    }

    protected List<PropertyProvider> getPropertyProviders() {
        return propertyProviders;
    }
}
