package ch.zhaw.it.prog2.functional.stream.primeprimitive;

import java.util.OptionalLong;
import java.util.stream.LongStream;

/**
 * Class provides static access to the method we used in
 * the concurrency lab
 */
public class PrimeHelper {

    private PrimeHelper() {
    }

    /**
     * Brute force check if submitted candidate is a prime number.
     *
     * @param primeCandidate Number to check if it is a prime
     * @return 0 if prime number, smallest factor otherwise
     * @throws IllegalArgumentException if primeCandidate is < 1
     */
    public static OptionalLong findSmallestFactor(long primeCandidate) {
        if (primeCandidate < 1) {
            throw new IllegalArgumentException("only numbers > 0 are supported");
        }
        if (primeCandidate > 3) {
            for (long factor = 2; factor <= Math.sqrt(primeCandidate); ++factor) {
                if (primeCandidate / factor * factor == primeCandidate) {
                    return OptionalLong.of(factor); // found a factor -> is no prime
                }
            }
        }
        return OptionalLong.empty(); // is prime number
    }

    /**
     * Return primes found in the stream of candidates
     *
     * @param candidates stream with numbers to be tested
     * @return stream with oll found candidates
     */
    public static LongStream findPrimes(LongStream candidates) {
        return candidates.filter(candidate -> findSmallestFactor(candidate).isEmpty());
    }

}
