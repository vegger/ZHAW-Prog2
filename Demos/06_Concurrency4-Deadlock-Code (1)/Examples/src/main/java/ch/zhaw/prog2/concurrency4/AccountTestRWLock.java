package ch.zhaw.prog2.concurrency4;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * AccountTestSafe.java
 *
 * Bemerkung: for-loop counter (von AccountThread) muss
 * je nach Rechner sehr gross sein, damit die Threads ueberhaupt
 * parallel laufen (sonst 1. Thread bereits beendet, wenn der 2. Thread startet...)
 */

public class AccountTestRWLock {
    public static final int LOOPS = 100_000;
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            Account account = new Account(0, 10000);
            Thread r1 = new AccountReadThread("Run-" + i + "-1", account);
            Thread r2 = new AccountReadThread("Run-" + i + "-2", account);
            Instant startRead = Instant.now();
            r1.start();
            r2.start();
            try {
                r1.join();
                r2.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Duration readDuration = Duration.between(startRead, Instant.now());
            System.out.println(" Read: Final Amount (Run " + i + "): " + account.getBalance()
                + " Duration: " + readDuration.toNanos());


            Thread w1 = new AccountWriteThread("Run-" + i + "-1", account, 1);
            Thread w2 = new AccountWriteThread("Run-" + i + "-2", account, -1);
            Instant startWrite = Instant.now();
            w1.start();
            w2.start();
            // wait until both threads terminated
            try {
                w1.join();
                w2.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Duration writeDuration = Duration.between(startWrite, Instant.now());
            // Print balance
            System.out.println("Write: Final Amount (Run " + i + "): " + account.getBalance()
                + " Duration: " + writeDuration.toNanos());
        }
    }

    private static class AccountWriteThread extends Thread {
        private final int amount;
        private final Account account;

        public AccountWriteThread(String name, Account account, int amount) {
            super(name);
            this.amount = amount;
            this.account = account;
        }

        @Override
        public void run() {
            System.out.println("STARTED write: " + getName());
            Instant startTime = Instant.now();
            for (int i = 0; i < LOOPS; i++) {
                account.transferAmount(amount);
            }
            Duration duration = Duration.between(startTime, Instant.now());
            System.out.println("ENDED write: " + getName() + " duration(ns): " + duration.toNanos());
        }
    }

    private static class AccountReadThread extends Thread {
        private final Account account;

        public AccountReadThread(String name, Account account) {
            super(name);
            this.account = account;
        }

        @Override
        public void run() {
            Instant startTime = Instant.now();
            System.out.println("STARTED read only: " + getName());
            for (int i = 0; i < LOOPS; i++) {
                account.getBalance();
            }
            Duration duration = Duration.between(startTime, Instant.now());
            System.out.println("ENDED read only: " + getName() + " duration(ns): " + duration.toNanos());
        }
    }

    private static class Account {
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock readLock = rwLock.readLock();
        private final Lock writeLock = rwLock.writeLock();
        private final int id;
        private int balance = 0;

        public Account(int id, int initialAmount) {
            this.id = id;
            this.balance = initialAmount;
        }

        public int getId() {
            return id;
        }

        public int getBalance() {
            readLock.lock();
            try {
                wasteTime();
                return balance;
            } finally {
                readLock.unlock();
            }
        }

        public void setBalance(int amount) {
            writeLock.lock();
            try {
                wasteTime();
                this.balance = amount;
            } finally {
                writeLock.unlock();
            }
        }

        public void transferAmount(int amount) {
            writeLock.lock();
            try {
                wasteTime();
                this.balance = this.balance + amount;
            } finally {
                writeLock.unlock();
            }
        }

        private double wasteTime() {
            double dummy= 0;
            for (int i = 100; i > 0; i--) {
                dummy = Math.sqrt(i) + Math.cos(dummy);
            }
            return dummy;
        }
    }
}


