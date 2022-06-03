package ch.zhaw.prog2.concurrency4;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalSyncQueue<E> implements SyncQueue<E> {

    private final Lock mutex = new ReentrantLock();
    private final Condition notEmpty = mutex.newCondition();
    private final Condition notFull = mutex.newCondition();
    private final LinkedList<E> queueList = new LinkedList<>();
    private final int capacity;

    public ConditionalSyncQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int count() {
        return queueList.size();
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public void add(E item) throws InterruptedException {
        mutex.lock(); // enter critical section
        try {
            while (queueList.size() >= capacity) {
                notFull.await();
            }
            queueList.addLast(item);
            notEmpty.signal();
        } finally {
            mutex.unlock();
        } // exit critical section
    }

    @Override
    public E remove() throws InterruptedException {
        E item = null;
        mutex.lock(); // enter critical section
        try {
            while (queueList.isEmpty()) {
                notEmpty.await();
            }
            item = queueList.removeFirst();
            notFull.signal();
        } finally {
            mutex.unlock();
        } // exit critical section
        return item;
    }

    @Override
    public synchronized E peek(int position) throws InterruptedException {
        if (position >= count()) {
            throw new ArrayIndexOutOfBoundsException(position);
        }
        return queueList.get(position);
    }
}
