package ch.zhaw.prog2.concurrency4;

import java.util.LinkedList;

public class ListSyncQueue<E> implements SyncQueue<E> {

    private final LinkedList<E> queueList = new LinkedList<>();
    private final int capacity;

    public ListSyncQueue(int capacity) {
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
    public synchronized void add(E item) throws InterruptedException {
        while (count() >= capacity) {
            wait(); // wait for free space
        }
        queueList.addLast(item);
        notifyAll(); // wakeup threads waiting in remove
    }

    @Override
    public synchronized E remove() throws InterruptedException {
        while (queueList.isEmpty()) {
            wait(); // wait for new item
        }
        E item = queueList.removeFirst();
        notifyAll(); // wakeup threads, waiting in addLast
        return item;
    }

    @Override
    public synchronized E peek(int position)  {
        if (position >= count()) {
            throw new IndexOutOfBoundsException(position);
        }
        return queueList.get(position);
    }

}
