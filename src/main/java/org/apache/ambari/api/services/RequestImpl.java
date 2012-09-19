package org.apache.ambari.api.services;

import org.apache.ambari.api.resource.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 9/5/12
 * Time: 8:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestImpl implements Request {

    private UriInfo m_uriInfo;
    private HttpHeaders m_headers;
    private RequestType m_requestType;
    private ResourceDefinition m_resourceDefinition;


    public RequestImpl(HttpHeaders headers, UriInfo uriInfo, RequestType requestType, ResourceDefinition resourceDefinition) {
        m_uriInfo = uriInfo;
        m_headers = headers;
        m_requestType = requestType;
        m_resourceDefinition = resourceDefinition;
    }

    @Override
    public ResourceDefinition getResource() {
        return m_resourceDefinition;
    }

    @Override
    public URI getURI() {
        return m_uriInfo.getRequestUri();
    }

    @Override
    public RequestType getRequestType() {
        return m_requestType;
    }

    @Override
    public int getAPIVersion() {
        return 0;
    }

    @Override
    public Map<String, List<String>> getQueryParameters() {
        return m_uriInfo.getQueryParameters();
    }

    @Override
    public Map<String, List<String>> getQueryPredicates() {
        //todo: handle expand/fields ...
        return getQueryParameters();
    }

    @Override
    public Set<String> getPartialResponseFields() {
        return null;
    }

    @Override
    public Set<String> getExpandEntities() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return m_headers.getRequestHeaders();
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public Serializer getSerializer() {
        return new JSONSerializer();
    }
}
