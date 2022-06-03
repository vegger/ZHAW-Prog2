package ch.zhaw.prog2.concurrency3;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Show busy waiting using compareAndSet-Method of AtomicBoolean
 *
 * Bemerkung: for-loop counter (von AccountThread) muss
 * je nach Rechner sehr gross sein, damit die Threads ueberhaupt
 * parallel laufen (sonst 1. Thread bereits beendet, wenn der 2. Thread startet...)
 */

public class AccountTestLockCompareAndSet {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Account account = new Account(0, 10_000);
            Thread t1 = new AccountThread("Run-" + i + "-1", account, 1);
            Thread t2 = new AccountThread("Run-" + i + "-2", account, -1);
            t1.start();
            t2.start();
            // wait until both threads terminated
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            // Print balance
            System.out.println("Final Amount (Run " + i + "): " + account.getBalance());
        }
    }

    private static class AccountThread extends Thread {
        private final int amount;
        private final Account account;

        public AccountThread(String name, Account account, int amount) {
            super(name);
            this.amount = amount;
            this.account = account;
        }

        @Override
        public void run() {
            System.out.println("STARTED: " + getName());
            for (int i = 0; i < 10_000; i++) {
                account.transferAmount(amount);
            }
            System.out.println("ENDED: " + getName());
        }
    }

    private static class Account {
        private final int id;
        private int balance = 0;
        private volatile AtomicBoolean locked = new AtomicBoolean(false);

        public Account(int id, int initialAmount) {
            this.id = id;
            this.balance = initialAmount;
        }

        public int getId() {
            return id;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int amount) {
            this.balance = amount;
        }

        public void transferAmount(int amount) {
            while (!locked.compareAndSet(false, true)) {  } //busy waiting
            // enter critical section when set to false by another thread -> lock by setting to true
            this.balance += amount;
            locked.set(false);  // release lock when leaving critical section
        }

    }
}


