package org.apache.ambari.api.query;

import org.apache.ambari.api.services.Result;
import org.apache.ambari.api.spi.PropertyId;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/10/12
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Query {
    public void addAllProperties(Map<String, Set<String>> setProperties);

    public void addProperty(String path, String property);
    public void addProperty(PropertyId property);
    //todo: signature - need path
    public void retainAllProperties(Set<String> setFields);
    public void clearAllProperties();

    public Result execute();
}
