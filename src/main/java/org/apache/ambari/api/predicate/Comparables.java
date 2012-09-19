package org.apache.ambari.api.predicate;

/**
 *
 */
public class Comparables {

    public static Comparable<String> forInteger(final Integer value) {

        return new Comparable<String>() {
            @Override
            public int compareTo(String s) {
                return value.compareTo(Integer.valueOf(s));
            }
        };
    }

    public static Comparable<String> forFloat(final Float value) {

        return new Comparable<String>() {
            @Override
            public int compareTo(String s) {
                return value.compareTo(Float.valueOf(s));
            }
        };
    }

    public static Comparable<String> forDouble(final Double value) {

        return new Comparable<String>() {
            @Override
            public int compareTo(String s) {
                return value.compareTo(Double.valueOf(s));
            }
        };
    }

    public static Comparable<String> forLong(final Long value) {

        return new Comparable<String>() {
            @Override
            public int compareTo(String s) {
                return value.compareTo(Long.valueOf(s));
            }
        };
    }

}
