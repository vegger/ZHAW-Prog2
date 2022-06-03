package ch.zhaw.prog2.concurrency4.philosopher;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ch.zhaw.prog2.concurrency4.philosopher.ForkManager.ForkState.*;

/**
 * ForkManager manages the resources (=forks), used by the philosophers
 */
class ForkManager {
    private static final LockFreeMax concurrentAcquires = new LockFreeMax();

    private final Lock mutex;
    private final int delayTime;
    private final int numForks;
    private final Fork[] forks;

    public ForkManager(int numForks, int delayTime) {
        this.mutex = new ReentrantLock();
        this.delayTime = delayTime;
        this.numForks = numForks;
        this.forks = new Fork[numForks];
        for (int forkId = 0; forkId < numForks; forkId++)
            forks[forkId] = new Fork(forkId, mutex);
    }

    /**
     * Acquire both forks of a specific philosopher.
     * Here you implement your synchronization strategy.
     * There should be always a delay between taking the two forks.
     *
     * @param philosopherId id of the philosopher
     */
    public void acquireForks(int philosopherId) throws InterruptedException {
        // acquire forks sequentially
        leftFork(philosopherId).acquire(philosopherId);
        forkDelay(delayTime);
        rightFork(philosopherId).acquire(philosopherId);
    }

    /**
     * Release both forks for a specific philosopher.
     * There should be always a delay between releasing the two forks.
     *
     * @param philosopherId id of the philosopher
     */
    public void releaseForks(int philosopherId) throws InterruptedException {
        // order of releasing does not matter
        leftFork(philosopherId).release(philosopherId);
        forkDelay(delayTime);
        rightFork(philosopherId).release(philosopherId);
    }

    private Fork rightFork(int philosopherId) {
        return forks[(numForks + philosopherId - 1) % numForks];
    }

    private Fork leftFork(int philosopherId) {
        return forks[philosopherId];
    }

    void forkDelay(int timeInMillis) throws InterruptedException {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            throw new InterruptedException("Interrupted fork delay - " + e.getMessage());
        }
    }

    public int getConcurrentAcquires() {
        return concurrentAcquires.maxValue.intValue();
    }

    public boolean areAllForksInState(ForkState state) {
    	return Arrays.stream(forks).allMatch(fork -> fork.state == state);
    }

    @Override
    public String toString() {
        return "forks = " + Arrays.toString(forks);
    }

    enum ForkState {
        FREE, WAITING, OCCUPIED
    }

    /**
     * Class holding the state of a single fork.
     * It also provides a condition (waiting room) to allow philosophers to wait for this fork.
     */
    private static class Fork {
        private final int id;
        private final Lock mutex;
        private final Condition cond;
        private ForkState state = FREE;
        private int ownerId;

        public Fork(int id, Lock mutex) {
            this.id = id;
            this.ownerId = -1;
            this.mutex = mutex;
            this.cond = mutex.newCondition();
        }

        public void acquire(int applicantId) throws InterruptedException {
            try {
                mutex.lock();
                while (state != FREE) {
                    state = WAITING;
                    cond.await();
                }
                concurrentAcquires.increment();
                TimeUnit.MILLISECONDS.sleep(10);
                state = OCCUPIED;
                ownerId = applicantId;
            } catch (InterruptedException e) {
                throw new InterruptedException("Interrupted acquire fork " + id + " - " + e.getMessage());
            } finally {
                concurrentAcquires.decrement();
                mutex.unlock();
            }
        }

        public void release(int applicantId) throws InterruptedException {
            try {
                mutex.lock();
                if (ownerId != applicantId) throw new IllegalStateException("Release fork " + id + " not owned by " + applicantId);
                TimeUnit.MILLISECONDS.sleep(10);
                state = FREE;
                ownerId = -1;
                cond.signal();
            } catch (InterruptedException e) {
                throw new InterruptedException("Interrupted release fork " + id + " - " + e.getMessage());
            } finally {
                mutex.unlock();
            }
        }

        @Override
        public String toString() {
            return "Fork {" + "id=" + id + ", state=" + state + ", owner=" + ownerId + '}';
        }
    }

    /*
     * Determine maximum value without using locks
     *
     * This class is used here to detect the maximum numbers of threads that can be in the same codeblock.
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
