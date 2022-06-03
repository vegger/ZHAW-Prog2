package ch.zhaw.it.prog2.functional.assign;

import java.util.Iterator;
import java.util.List;
import java.util.function.ToLongBiFunction;

/**
 * Aggregate values of an integer list.
 * Used for PROG2 slides in "11 - Functional Programming" in Annex
 */
public class Aggregate {
    private Aggregate() {
    }

    /**
     * Aggregates the values, starting with an aggregated value of 0.
     * Only usable for adding / subtracting values because multiplying with 0 will be 0.
     *
     * @param function aggregate function
     * @param values   integer values to aggregate
     * @return aggregated value, 0 if the list is empty
     */
    public static long aggregate(ToLongBiFunction<Long, Integer> function, List<Integer> values) {
        long aggregation = 0;
        for (int element : values) {
            aggregation = function.applyAsLong(aggregation, element);
        }
        return aggregation;
    }

    /**
     * Implementation taking the first element to initialise aggregate variable.
     * Can also be used to multiply the values
     *
     * @param function aggregate function
     * @param values   integer values to aggregate, list must contain at least on element or an IllegalArgumentException
     *                 is thrown.
     * @return aggregated value
     */
    public static long aggregateWithFirstElement(ToLongBiFunction<Long, Integer> function, List<Integer> values) {
        Iterator<Integer> iterator = values.listIterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("List must have at least one element.");
        }
        long aggregation = iterator.next();
        while (iterator.hasNext()) {
            aggregation = function.applyAsLong(aggregation, iterator.next());
        }
        return aggregation;
    }

    /**
     * Non functional implementation
     *
     * @param values List of Integers
     * @return sum of the integers in the list
     */
    public static long calculateSumOnListElements(List<Integer> values) {
        long sum = 0;
        for (int element : values) {
            sum += element;
        }
        return sum;
    }

    public static ToLongBiFunction<Long, Integer> functionFactory() {
        return (longValue, intValue) -> longValue + intValue;
    }

}
