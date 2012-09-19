package org.apache.ambari.api.predicate;

import junit.framework.Assert;
import org.apache.ambari.api.internal.PropertyIdImpl;
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
        ArrayPredicate      visitedArrayPredicate      = null;
        UnaryPredicate      visitedUnaryPredicate      = null;

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
