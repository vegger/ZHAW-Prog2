package ch.zhaw.it.prog2.functional.stream.primeprimitive;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.OptionalLong;
import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Show the effect of parallelStream using the brute force prime checker
 * from concurrency lab, which has been adopted to use {@link OptionalLong}
 * and an internal class to combine parameter and result in one object.
 * <p>
 * To have parameter and result at the same time in a stream, you need an
 * object that combines them.
 */
public class ParallelStream {
    private static final long LOWER_LIMIT = 10_000L;
    private static final long UPPER_LIMIT = 1_000_000_000L;
    private static final int NUM_PRIME = 500_000;           // adjust according your cpu power
    private static final long SEED = 33;

    public static void main(String[] args) {
        new ParallelStream().measureTime(getCandidateStream());
        new ParallelStream().measureTime(getCandidateStream().parallel());
    }

    private void measureTime(LongStream candidates) {
        Instant start = Instant.now();

        LongStream primes = PrimeHelper.findPrimes(candidates).sorted();
        Instant streamBuild = Instant.now();

        List<Long> primesList = primes.boxed().toList();
        Instant putInCollection = Instant.now();

        System.out.printf("%nNeeded %4.2f seconds for %d candidates, %d primes found. %4.2f seconds for build stream and %4.2f seconds to put into a collection.%n",
            Duration.between(start, putInCollection).toMillis() / 1000.0,
            NUM_PRIME,
            primesList.size(),
            Duration.between(start, streamBuild).toMillis() / 1000.0,
            Duration.between(streamBuild, putInCollection).toMillis() / 1000.0);
    }

    /**
     * Generate the same stream of random numbers on every call
     *
     * @return random stream of numbers
     */
    private static LongStream getCandidateStream() {
        Stream<Long> candidates = new Random(SEED).longs(LOWER_LIMIT, UPPER_LIMIT).limit(NUM_PRIME).boxed();
        // Random does create other numbers in a parallel stream then in a sequential stream.
        // To get the same numbers, we create a list of numbers and use this as a stream source
        List<Long> candidateList = candidates.toList();
        return candidateList.stream().mapToLong(e -> e);
    }
}



