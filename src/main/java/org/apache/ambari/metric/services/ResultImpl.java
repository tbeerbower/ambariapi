package org.apache.ambari.metric.services;


import org.apache.ambari.metric.spi.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/5/12
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
//todo: at the moment only supports one level of nesting.
//todo: need to allow arbitrary nesting depth for expansion.
//todo: consider building a tree structure.
public class ResultImpl implements Result {

    private Map<String, List<Resource>> m_mapResources = new HashMap<String, List<Resource>>();

    @Override
    public void addResources(String groupName, List<Resource> listResources) {
        List<Resource> resources = m_mapResources.get(groupName);
        if (resources == null) {
            m_mapResources.put(groupName, listResources);
        } else {
            resources.addAll(listResources);
        }
    }

    @Override
    public Map<String, List<Resource>> getResources() {
        return m_mapResources;
    }
}

