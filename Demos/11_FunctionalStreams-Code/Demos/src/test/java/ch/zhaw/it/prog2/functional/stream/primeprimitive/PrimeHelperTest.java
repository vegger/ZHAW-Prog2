package ch.zhaw.it.prog2.functional.stream.primeprimitive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.OptionalLong;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class PrimeHelperTest {
    private static final long LOWER_LIMIT = 10000L;
    private static final long UPPER_LIMIT = 1000000000L;
    private static final int NUM_PRIME = 100;
    private static final long SEED = 33;

    private LongStream candidates;
    private Set<Long> candidateSet;

    @BeforeEach
    void setUp() {
        candidateSet = new Random(SEED).longs(LOWER_LIMIT, UPPER_LIMIT).limit(NUM_PRIME).boxed().collect(Collectors.toSet());
        candidates = candidateSet.stream().mapToLong(e -> e);
    }

    @Test
    void sequentialStream() {
        assertIterableEquals(candidateSet, candidates.boxed().collect(Collectors.toSet()), "Random stream has to generate same numbers every time");
    }

    @Test
    void parallelStream() {
        assertIterableEquals(candidateSet, candidates.parallel().boxed().collect(Collectors.toSet()), "Random stream has to generate same numbers also in parallel");
    }

    @Test
    void findPrimes() {
        List<Long> found = PrimeHelper.findPrimes(LongStream.of(3, 4, 7)).boxed().collect(Collectors.toList());
        assertIterableEquals(List.of(3L, 7L), found);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 5, 7, 11, 991, 997, 9973})
    void findSmallestFactorForPrimes(long primeNumber) {
        assertEquals(OptionalLong.empty(), PrimeHelper.findSmallestFactor(primeNumber), "prime numbers are reported with empty optional");
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -2, -22})
    void findSmallestFactorForUnsupportedValues(long number) {
        assertThrows(IllegalArgumentException.class, () -> PrimeHelper.findSmallestFactor(number));
    }

    @ParameterizedTest(name = "[{index}] {0} has smallest factor {1}")
    @CsvSource({
        "4, 2",
        "8, 2",
        "27, 3",
        "24, 2",
        "35, 5",
        "77, 7",
        "988027, 991"
    })
    void findSmallestFactorForPrimes2(long nonPrimeNumber, long smallestFactor) {
        assertEquals(OptionalLong.of(smallestFactor), PrimeHelper.findSmallestFactor(nonPrimeNumber));
    }
}
