package org.apache.ambari.api.services;


import org.apache.ambari.api.spi.Resource;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/5/12
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Result {
    public void addResources(String groupName, List<Resource> listResources);
    public Map<String, List<Resource>> getResources();
}
