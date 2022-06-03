package ch.zhaw.prog2.concurrency4.philosopher;

import java.util.Arrays;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ch.zhaw.prog2.concurrency4.philosopher.PhilosopherTable.PhilosopherState.*;
import static ch.zhaw.prog2.concurrency4.philosopher.ForkManager.ForkState.*;

/**
 * PhilosopherTable represent the model.
 * It is responsible to create, manage and terminate the {@link Philosopher} threads.
 */
class PhilosopherTable extends Observable  {
    private final int baseTime;
    private final int philosopherCount;
    private final Philosopher[] philosophers;
    private final ForkManager forkManager;
    private volatile boolean running;
    private ExecutorService philosopherExecutor;
    private ScheduledExecutorService watchdogExecutor;

    public PhilosopherTable(int philosopherCount, int baseTime) {
        this.baseTime = baseTime;
        this.philosopherCount = philosopherCount;
        this.philosophers = new Philosopher[philosopherCount];
        this.forkManager = new ForkManager(philosopherCount, baseTime);
        for (int philosopherId = philosopherCount - 1; philosopherId >= 0; philosopherId--) {
            philosophers[philosopherId] = new Philosopher(philosopherId);
        }
        notifyStateChange(null);
        System.out.printf("Creating table (%d Philosophers, base time = %dms )%n ...", philosopherCount, baseTime);
    }

    private synchronized void notifyStateChange(Philosopher sender) {
        setChanged();
        notifyObservers(sender);
        checkNeighbourState(sender);
    }

    private void checkNeighbourState(Philosopher philosopher) {
        if (philosopher != null && philosopher.state == EATING) {
            int id = philosopher.id;
            PhilosopherState leftState = philosophers[leftNeighbourId(id)].state;
            PhilosopherState rightState = philosophers[rightNeighbourId(id)].state;
            int eatingNeighbour = leftState  == EATING ? leftNeighbourId(id) :
                                  rightState == EATING ? rightNeighbourId(id) :
                                  -1;
            if (eatingNeighbour >= 0) {
                System.out.println("ILLEGAL STATE: Two neighbouring Philosophers are eating: " + id + " | " + eatingNeighbour);
                stop();
            }
        }
    }

    private void checkDeadlock() {
        if (forkManager.areAllForksInState(WAITING) && areAllPhilosophersInState(HUNGRY)) {
            System.out.println("DEADLOCK: All Philosophers are starving!!!");
            stop();
        }
    }

    private boolean areAllPhilosophersInState(PhilosopherState state) {
        return Arrays.stream(philosophers).allMatch(philosopher -> philosopher.state == state );
    }


    public void start() {
        this.running = true;
        System.out.println("Start deadlock watchdog ...");
        watchdogExecutor = Executors.newSingleThreadScheduledExecutor();
        watchdogExecutor.scheduleAtFixedRate(this::checkDeadlock, 2, 2, TimeUnit.SECONDS);
        System.out.println("Starting philosophers ...");
        philosopherExecutor = Executors.newFixedThreadPool(philosopherCount);
        for (Philosopher philosopher : philosophers) {
            philosopherExecutor.execute(philosopher);
        }
    }

    public void stop() {
        if (running) {
            this.running = false;
            System.out.println("Stopping deadlock watchdog ...");
            watchdogExecutor.shutdown();
            System.out.println("Stopping philosophers ...");
            philosopherExecutor.shutdownNow();
            System.out.println("Final state: \n" + this);
            System.out.format("Detected at most %d concurrent Philosophers acquiring forks%n", forkManager.getConcurrentAcquires());
        } else {
            System.err.println("Stop called while not running.");
        }
    }

    public Philosopher getPhilosopher(int philosopherId) {
        return philosophers[philosopherId];
    }

    public int rightNeighbourId(int philosopherId) {
        return (philosopherCount + philosopherId - 1) % philosopherCount;
    }

    public int leftNeighbourId(int philosopherId) {
        return (philosopherId + 1) % philosopherCount;
    }

    @Override
    public String toString() {
        return "PhilosopherTable { running = " + running +
            "\n  philosophers = " + Arrays.toString(philosophers) +
            "\n  " + forkManager +
            "\n}";
    }

    enum PhilosopherState {
        THINKING, HUNGRY, EATING
    }

    /**
     * Implementation of the Philosopher as an inner class.
     * The {@link PhilosopherTable#running} variable from the outer class {@link PhilosopherTable} is used to control
     * termination of the run loop.
     * Also, to notify the observers (GUI) the {@link PhilosopherTable#notifyStateChange(Philosopher)} method of
     * the outer class table is used.
     */
     class Philosopher implements Runnable {
        private static final int THINK_TIME_FACTOR = 5;
        private static final int EAT_TIME_FACTOR = 1;
        private final int id;
        private PhilosopherState state = THINKING;

        public Philosopher(int id) {
            this.id = id;
        }

        public PhilosopherState getState() {
            return state;
        }

        public long getId() {
            return id;
        }

        private void think() throws InterruptedException {
            try {
                state = THINKING;
                notifyStateChange(this);
                Thread.sleep((int) (Math.random() * THINK_TIME_FACTOR * baseTime));
            } catch (InterruptedException e) {
                throw new InterruptedException("Interrupted thinking - " + e.getMessage());
            }
        }

        private void eat() throws InterruptedException {
            try {
                state = EATING;
                notifyStateChange(this);
                Thread.sleep((int) (Math.random() * EAT_TIME_FACTOR * baseTime));
            } catch (InterruptedException e) {
                throw new InterruptedException("Interrupted eating - " + e.getMessage());
            }
        }

        private void takeForks() throws InterruptedException {
            state = HUNGRY;
            notifyStateChange(this);
            forkManager.acquireForks(id);
        }

        private void putForks() throws InterruptedException {
            state = THINKING;  // needed to prevent false positive in checkNeighborState
            forkManager.releaseForks(id);
        }

        @Override
        public void run() {
            System.out.println("Starting Philosopher " + id);
            try {
                while (running) {
                    think();
                    takeForks();
                    eat();
                    putForks();
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted " + this + " : " + e.getMessage());
            }
            System.out.println("Stopping Philosopher " + id);
        }

        @Override
        public String toString() {
            return "Philosopher {" + "id=" + id + ", state=" + state + "}";
        }
    }
}
