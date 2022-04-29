import java.util.TreeSet;
import java.util.Collection;
import java.util.Iterator;

interface Updateable { void update(int x); }

// Alternatives: Collections.syncrhonizedSortedSet and ReentrantReadWriteLock
public class CustomTreeSet<T extends Updateable> extends TreeSet<T> {

    public synchronized boolean update(T e, int val) {
        if(remove(e)) {
            e.update(val);
            System.out.println("update successful");
            return add(e);
        }
        return false;
    }

    @Override
    public synchronized boolean add(T e) {
        return super.add(e);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        return super.addAll(c);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return super.iterator();
    }

    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public synchronized int size() {
        return super.size();
    }
}
