package org.apache.ambari.api.services;

import org.apache.ambari.api.resource.ResourceDefinition;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/5/12
 * Time: 7:52 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Request {

    public enum RequestType {
        GET,
        PUT,
        POST,
        DELETE
    }

    public enum ResponseType { JSON }

    public ResourceDefinition getResource();
    public URI getURI();
    public RequestType getRequestType();
    public int getAPIVersion();
    public Map<String, List<String>> getQueryParameters();
    public Map<String, List<String>> getQueryPredicates();
    public Set<String> getPartialResponseFields();
    public Set<String> getExpandEntities();
    public Map<String, List<String>> getHeaders();
    public String getBody();
    public Serializer getSerializer();

    //todo: temporal information.  For now always specify in PR for each field.  Could use *[...] ?
    //public Map<String, TemporalData> getTemporalFields();
}
