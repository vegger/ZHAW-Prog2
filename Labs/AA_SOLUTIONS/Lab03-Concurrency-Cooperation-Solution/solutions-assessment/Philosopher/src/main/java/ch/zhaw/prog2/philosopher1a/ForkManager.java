package ch.zhaw.prog2.philosopher1a;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ch.zhaw.prog2.philosopher1a.ForkManager.ForkPairState.*;

/**
 * ForkManager manages the resources (=forks), used by the philosophers
 */
class ForkManager {
    // count maximum concurrent threads acquiring forks - used for internal statistics
    private static final LockFreeMax concurrentAcquiresCounter = new LockFreeMax();

    private final Lock managerLock;     // shared lock for all forks
    private final int delayTime;        // delay in milliseconds between acquiring / releasing the forks
    private final int numForkPairs;     // amount of fork-pairs to be managed
    private final ForkPair[] forkPairs; // array of managed fork-pairs

    /**
     * Constructor to initialize the fork manager.
     * @param numForkPairs amount of fork-pairs to manage
     * @param delayTime    delay in milliseconds between acquiring / releasing the forks
     */
    public ForkManager(int numForkPairs, int delayTime) {
        this.managerLock = new ReentrantLock();
        this.delayTime = delayTime;
        this.numForkPairs = numForkPairs;
        this.forkPairs = new ForkPair[numForkPairs];
        for (int forkPairId = 0; forkPairId < numForkPairs; forkPairId++)
            forkPairs[forkPairId] = new ForkPair(forkPairId, managerLock);
    }

    /**
     * Acquire both forks of a specific philosopher.
     * Here you implement your synchronization strategy.
     * @param philosopherId id of the philosopher
     */
    public void acquireForks(int philosopherId) throws InterruptedException {
        ForkPair thisForkPair = thisForkPair(philosopherId);
        try {
            managerLock.lock();
            // wait until left and right pairs are FREE
            while (!(thisForkPair.state == FREE &&
                leftForkPair(philosopherId).state == FREE &&
                rightForkPair(philosopherId).state == FREE)) {
                thisForkPair.pairFree.await();
            }
            // as soon both are free we are able to aquire them without locking
            thisForkPair.acquire(philosopherId);
        } catch (InterruptedException e) {
            throw new InterruptedException("Interrupted acquire forks - " + e.getMessage());
        } finally {
            managerLock.unlock();
        }
    }

    /**
     * Release both forks for a specific philosopher.
     * There should be always a delay between releasing the two forks.
     *
     * @param philosopherId id of the philosopher
     */
    public void releaseForks(int philosopherId) throws InterruptedException {
        try {
            managerLock.lock();
            // release this forkpair and signal waiting neighbours
            thisForkPair(philosopherId).release(philosopherId);
            leftForkPair(philosopherId).pairFree.signal();
            rightForkPair(philosopherId).pairFree.signal();
        } catch (InterruptedException e) {
            throw new InterruptedException("Interrupted release forks - " + e.getMessage());
        } finally {
            managerLock.unlock();
        }
    }

    /**
     * Returns the fork-pair of the given philosopher
     * @param philosopherId id of philosopher to get the fork-pair
     * @return fork-pair of the specified philosopher
     */
    private ForkPair thisForkPair(int philosopherId) {
        return forkPairs[philosopherId];
    }

    /**
     * Returns the fork-pair of the philosopher to the right
     * @param philosopherId id of philosopher
     * @return fork-pair of the philosopher to the right
     */
    private ForkPair rightForkPair(int philosopherId) {
        return forkPairs[(numForkPairs + philosopherId - 1) % numForkPairs];
    }

    /**
     * Returns the fork-pair of the philosopher to the left
     * @param philosopherId id of philosopher
     * @return fork-pair of the philosopher to the left
     */
    private ForkPair leftForkPair(int philosopherId) {
        return forkPairs[(philosopherId + 1) % numForkPairs];
    }

    /**
     * Get the maximum number of threads concurrently acquired forks - used to measure parallelism.
     * @return maximum number of concurrent threads acquiring forks
     */
    public int getConcurrentAcquires() {
        return concurrentAcquiresCounter.maxValue.intValue();
    }

    /**
     * Test if all fork-pairs are in a specific state. Used to detect deadlocks.
     * @param state State of fork-pair to test for
     * @return true if all fork-pairs are in the given state, false otherwise
     */
    public boolean areAllForkPairsInState(ForkPairState state) {
    	return Arrays.stream(forkPairs).allMatch(pairs -> pairs.state == state);
    }

    @Override
    public String toString() {
        return "fork pairs = " + Arrays.toString(forkPairs);
    }

    /**
     * Possible states of a fork-pairs.
     * The WAITING state is used as a temporary state to mark an OCCUPIED fork-pair,
     * if another philosopher is waiting for it (see {@link ForkPair#acquire(int)}.
     */
    enum ForkPairState {
        FREE, WAITING, OCCUPIED
    }

    /**
     * Class holding the state of a single fork-pair.
     * It also provides a condition (waiting room) to allow philosophers to wait for this fork.
     */
    private static class ForkPair {
        private final int id;               // unique id of fork-pair
        private final Lock managerLock;     // shared lock of the fork-manager
        private final Condition pairFree;   // specific wait condition for this fork-pair
        private ForkPairState state = FREE; // current state of the fork-pair
        private int ownerId;                // temporary owning philosopher of the fork

        /**
         * Constructor for a fork-pair of a philosopher.
         * @param id    unique id of the fork-pair
         * @param managerLock the ForkManager's shared lock to use for wait conditions
         */
        public ForkPair(int id, Lock managerLock) {
            this.id = id;
            this.ownerId = -1;
            this.managerLock = managerLock;
            this.pairFree = managerLock.newCondition();
        }

        /**
         * Acquire the fork for a specific philosopher (applicantId).
         * The applicantId is used to remember the current "owner" of a fork.
         * @param applicantId id of the philosopher to acquire the fork
         * @throws InterruptedException if the thread is interrupted during waiting for the fork
         */
        public void acquire(int applicantId) throws InterruptedException {
            try {
                managerLock.lock();
                while (state != FREE) {
                    state = WAITING;
                    pairFree.await();
                }
                concurrentAcquiresCounter.increment();
                TimeUnit.MILLISECONDS.sleep(10);
                state = OCCUPIED;
                ownerId = applicantId;
            } catch (InterruptedException e) {
                throw new InterruptedException("Interrupted acquire fork pair " + id + " - " + e.getMessage());
            } finally {
                concurrentAcquiresCounter.decrement();
                managerLock.unlock();
            }
        }

        /**
         * Release the fork for a specific philosopher (applicantId).
         * The applicantId is used to verify that a fork is released by the "owner".
         * @param applicantId id of the philosopher releasing the fork
         * @throws InterruptedException if the thread is interrupted during releasing the fork
         */
        public void release(int applicantId) throws InterruptedException {
            try {
                managerLock.lock();
                if (ownerId != applicantId) throw new IllegalStateException("Release fork pair " + id + " not owned by " + applicantId);
                TimeUnit.MILLISECONDS.sleep(10);
                state = FREE;
                ownerId = -1;
                pairFree.signal();
            } catch (InterruptedException e) {
                throw new InterruptedException("Interrupted release fork pair " + id + " - " + e.getMessage());
            } finally {
                managerLock.unlock();
            }
        }

        @Override
        public String toString() {
            return "ForkPair {" + "id=" + id + ", state=" + state + ", owner=" + ownerId + '}';
        }
    }

    /*
     * Determine maximum value without using locks
     *
     * This class is used here to detect the maximum numbers of threads that have been active in the same codeblock.
     * To get a good measurement:
     *  - Use a sleep() between increment() and decrement()
     *  - Use increment() after acquiring the lock and decrement() before releasing the lock
     *  - Do not call any method that does release the lock: Do not use await() between increment() and decrement()
     */
    private static class LockFreeMax {
        private final AtomicInteger value = new AtomicInteger(0);
        private final AtomicInteger maxValue = new AtomicInteger(0);

        public void increment() {
            maxValue.accumulateAndGet(value.incrementAndGet(), Math::max);
        }

        public void decrement() {
            value.decrementAndGet();
        }
    }
}
