package ch.zhaw.prog2.concurrency4;

public class AccountTransferTest {

    public static void main(String[] args) {
        Account account1 = new Account(1, 10);
        Account account2 = new Account(2, 10);
        Account account3 = new Account(3, 999999);


        System.out.println("Start of Transaction");
        System.out.println("Balance account1: " + account1.getBalance());
        System.out.println("Balance account2: " + account2.getBalance());
        System.out.println("Balance account3: " + account3.getBalance());

        System.out.println("Summed up balances account1 and account2 and account3: " +
                (account1.getBalance() + account2.getBalance() + account3.getBalance()));

        AccountTransferThread t1 = new AccountTransferThread("Worker 1", account3, account1, 1);
        AccountTransferThread t2 = new AccountTransferThread("Worker 2", account1, account2, 2);
        AccountTransferThread t3 = new AccountTransferThread("Worker 3", account2, account1, 1);

        t1.start();
        t2.start();
        t3.start();
        System.out.println("Working...");

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("End of Transaction");
        System.out.println("Balance account1: " + account1.getBalance());
        System.out.println("Balance account2: " + account2.getBalance());
        System.out.println("Balance account3: " + account3.getBalance());

        System.out.println("Summed up balances of account1 and account2 and account3 (should be the same as at start):" +
                (account1.getBalance() + account2.getBalance() + account3.getBalance()));
    }


    static class Account {
        private final int id;
        private int balance = 0;

        public Account(int id, int initialAmount) {
            this.id = id;
            this.balance = initialAmount;
        }

        public int getId() {
            return id;
        }

        public synchronized int getBalance() {
            return balance;
        }

        public synchronized void transferAmount(int amount) {
            this.balance += amount;
        }
    }


    static class AccountTransferThread extends Thread {

        private final Account fromAccount;
        private final Account toAccount;
        private final int amount;
        private final int NUM_ITTERATIONS = 10000;
        private int totalInc = 0;

        public AccountTransferThread(String str, Account fromAccount, Account toAccount, int amount) {
            super(str);
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.amount = amount;
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_ITTERATIONS; i++) {
                accountTransfer();
            }
            System.out.println("DONE! " + getName() + " Total Increments= " + totalInc);
        }

        public void accountTransfer() { // as a method
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    // Account must not be overdrawn
                    if (fromAccount.getBalance() >= amount) {
                        fromAccount.transferAmount(-amount);
                        toAccount.transferAmount(amount);
                        totalInc += amount;
                    } // end synchronized to
                } // end synchronized from
            }
        }

    }


}
