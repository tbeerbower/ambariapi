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
package org.apache.ambari.api.controller.predicate;

import junit.framework.Assert;
import org.apache.ambari.api.controller.internal.PropertyIdImpl;
import org.junit.Test;

/**
 *
 */
public class PredicateVisitorTest {

  @Test
  public void testVisitor() {

    PropertyIdImpl propertyId = new PropertyIdImpl("foo", "category1", false);
    EqualsPredicate equalsPredicate = new EqualsPredicate(propertyId, "bar");

    TestPredicateVisitor visitor = new TestPredicateVisitor();
    equalsPredicate.accept(visitor);

    Assert.assertSame(equalsPredicate, visitor.visitedComparisonPredicate);
    Assert.assertNull(visitor.visitedArrayPredicate);
    Assert.assertNull(visitor.visitedUnaryPredicate);

    AndPredicate andPredicate = new AndPredicate(equalsPredicate);

    visitor = new TestPredicateVisitor();
    andPredicate.accept(visitor);

    Assert.assertNull(visitor.visitedComparisonPredicate);
    Assert.assertSame(andPredicate, visitor.visitedArrayPredicate);
    Assert.assertNull(visitor.visitedUnaryPredicate);

    NotPredicate notPredicate = new NotPredicate(andPredicate);

    visitor = new TestPredicateVisitor();
    notPredicate.accept(visitor);

    Assert.assertNull(visitor.visitedComparisonPredicate);
    Assert.assertNull(visitor.visitedArrayPredicate);
    Assert.assertSame(notPredicate, visitor.visitedUnaryPredicate);
  }

  public static class TestPredicateVisitor implements PredicateVisitor {

    ComparisonPredicate visitedComparisonPredicate = null;
    ArrayPredicate visitedArrayPredicate = null;
    UnaryPredicate visitedUnaryPredicate = null;

    @Override
    public void acceptComparisonPredicate(ComparisonPredicate predicate) {
      visitedComparisonPredicate = predicate;
    }

    @Override
    public void acceptArrayPredicate(ArrayPredicate predicate) {
      visitedArrayPredicate = predicate;
    }

    @Override
    public void acceptUnaryPredicate(UnaryPredicate predicate) {
      visitedUnaryPredicate = predicate;
    }
  }

}
