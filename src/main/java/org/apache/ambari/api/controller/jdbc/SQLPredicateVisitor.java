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

package org.apache.ambari.api.controller.jdbc;

import org.apache.ambari.api.controller.predicate.ArrayPredicate;
import org.apache.ambari.api.controller.predicate.BasePredicate;
import org.apache.ambari.api.controller.predicate.ComparisonPredicate;
import org.apache.ambari.api.controller.predicate.PredicateVisitor;
import org.apache.ambari.api.controller.predicate.UnaryPredicate;
import org.apache.ambari.api.controller.spi.PropertyId;

/**
 *
 */
public class SQLPredicateVisitor implements PredicateVisitor {

  private final StringBuilder stringBuilder = new StringBuilder();

  @Override
  public void acceptComparisonPredicate(ComparisonPredicate predicate) {
    PropertyId propertyId = predicate.getPropertyId();

    if (propertyId.getCategory() != null) {
      stringBuilder.append(propertyId.getCategory()).append(".");
    }
    stringBuilder.append(propertyId.getName());

    stringBuilder.append(" ").append(predicate.getOperator()).append(" \"");
    stringBuilder.append(predicate.getValue());
    stringBuilder.append("\"");

  }

  @Override
  public void acceptArrayPredicate(ArrayPredicate predicate) {
    BasePredicate[] predicates = predicate.getPredicates();
    if (predicates.length > 0) {

      stringBuilder.append("(");
      for (int i = 0; i < predicates.length; i++) {
        if (i > 0) {
          stringBuilder.append(" ").append(predicate.getOperator()).append(" ");
        }
        predicates[i].accept(this);
      }
      stringBuilder.append(")");
    }
  }

  @Override
  public void acceptUnaryPredicate(UnaryPredicate predicate) {
    stringBuilder.append(predicate.getOperator()).append("(");
    predicate.getPredicate().accept(this);
    stringBuilder.append(")");
  }


  public String getSQL() {
    return stringBuilder.toString();
  }
}
