package ch.zhaw.it.prog2.functional.assign;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToLongBiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AggregateTest {

    @Test
    void calculateSumOnListElements() {
        List<Integer> values = Arrays.asList(13, 17, 23);
        long result = Aggregate.calculateSumOnListElements(values);
        assertEquals(53, result, "Sum of 13+17+23 is 53");
    }

    @Test
    void aggregate() {
        List<Integer> values = Arrays.asList(5, 7, 11);
        ToLongBiFunction<Long, Integer> sumFunction = (oldSUm, newValue) -> oldSUm + newValue;
        long result = Aggregate.aggregate(sumFunction, values);
        assertEquals(23, result, "Sum of 5, 7, 11 is 23");

        values = Arrays.asList(2, 3, 5);
        ToLongBiFunction<Long, Integer> sumSquare = (oldSum, newValue) -> oldSum + newValue * newValue;
        result = Aggregate.aggregate(sumSquare, values);
        assertEquals(38, result, "Square-Sum of 2, 3, 5 is 38");
    }

    @Test
    void functionFactory() {
        ToLongBiFunction<Long, Integer> sumFunction = Aggregate.functionFactory();
        assertNotNull(sumFunction);
        assertEquals(5, sumFunction.applyAsLong(2L, 3));
        assertEquals(7, sumFunction.applyAsLong(0L, 7));
        assertEquals(11, sumFunction.applyAsLong(11L, 0));
        assertEquals(0, sumFunction.applyAsLong(0L, 0));
        assertEquals(-140, sumFunction.applyAsLong(-123L, -17));
    }

    @Test
    void aggregateWithFirstElement() {
        List<Integer> values = Arrays.asList(5, 7, 11);
        ToLongBiFunction<Long, Integer> sumFunction = (oldSUm, newValue) -> oldSUm + newValue;
        long result = Aggregate.aggregateWithFirstElement(sumFunction, values);
        assertEquals(23, result, "Sum of 5, 7, 11 is 23");

        ToLongBiFunction<Long, Integer> factorizeFunction = (oldProduct, newFactor) -> oldProduct * newFactor;
        result = Aggregate.aggregateWithFirstElement(factorizeFunction, values);
        assertEquals(385, result, "Product of 5, 7, 11 is 385");
    }
}
