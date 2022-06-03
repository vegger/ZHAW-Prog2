package ch.zhaw.prog2.concurrency4;

public interface SyncQueue<E> {

    public abstract int count();
    public abstract int capacity();
    public abstract void add(E item) throws InterruptedException;
    public abstract E remove() throws InterruptedException;
    public abstract E peek(int position) throws InterruptedException;

}
