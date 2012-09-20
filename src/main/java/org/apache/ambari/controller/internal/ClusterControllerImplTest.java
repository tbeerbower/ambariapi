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

import org.apache.ambari.controller.spi.ClusterController;
import org.apache.ambari.controller.spi.Predicate;
import org.apache.ambari.controller.spi.PropertyId;
import org.apache.ambari.controller.spi.Request;
import org.apache.ambari.controller.spi.Resource;
import org.apache.ambari.controller.utilities.PredicateBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ClusterControllerImplTest {

    @Ignore @Test
    public void testGetResources() throws Exception {

        // create a cluster controller
        ClusterController cc = ClusterControllerImpl.getSingleton();

        // populate the request object with the categories that we want to get
        Set<PropertyId> propertyIds = new HashSet<PropertyId>();
//        propertyIds.add(new PropertyIdImpl("cluster_name"  , "HostRoles", false));
//        propertyIds.add(new PropertyIdImpl("host_name"     , "HostRoles", false));
//        propertyIds.add(new PropertyIdImpl("component_name", "HostRoles", false));
//        propertyIds.add(new PropertyIdImpl("state"         , "HostRoles", false));

        Request request = new RequestImpl(propertyIds);

//        EqualsPredicate equalsPredicate1 = new EqualsPredicate(new PropertyIdImpl("component_name", "HostRoles", false), "NAMENODE");
//        EqualsPredicate equalsPredicate2 = new EqualsPredicate(new PropertyIdImpl("component_name", "HostRoles", false), "DATANODE");
//        OrPredicate orPredicate = new OrPredicate(equalsPredicate1, equalsPredicate2);

        Predicate predicate = new PredicateBuilder().
                property("component_name", "HostRoles").equals("NAMENODE").or().
                property("component_name", "HostRoles").equals("DATANODE").
                toPredicate();

        // request the host_component resources
        getResources(Resource.Type.HostComponent, cc, request, predicate);

        // request the hosts; predicate is null so we'll get them all
        getResources(Resource.Type.Host, cc, request, null);

        // request the clusters; predicate is null so we'll get them all
        getResources(Resource.Type.Cluster, cc, request, null);

        // request the services; predicate is null so we'll get them all
        getResources(Resource.Type.Service, cc, request, null);


        PropertyId serviceIdProperty = cc.getSchema(
                Resource.Type.Service).getKeyPropertyId(Resource.Type.Service);


        // request the components; predicate is null so we'll get them all
        getResources(Resource.Type.Component, cc, request, null);

    }

    private void getResources(Resource.Type type, ClusterController cc, Request request, Predicate predicate) {
        Iterable<Resource> i;
        int cnt;
        i = cc.getResources(type, request, predicate);

        cnt = 0;
        for (Resource r : i) {
            System.out.println( r + "\n\n");
            ++cnt;
        }
        System.out.println("Number of resources " + cnt);
    }
}


