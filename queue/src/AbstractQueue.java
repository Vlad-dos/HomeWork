import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    protected abstract void doEnqueue(Object a);

    protected abstract Object doDequeue();

    protected abstract Object doElement();

    protected abstract void doClear();

    protected abstract Queue makeQueue();

    public void enqueue(Object a) {
        doEnqueue(a);
        size++;
    }

    public Object element() {
        return doElement();
    }

    public Object dequeue() {
        size--;
        return doDequeue();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        doClear();
    }

    public Queue map(Function<Object, Object> function) {
        Queue newQueue = makeQueue();
        int sz = size;
        for (int i = 0; i < sz; i++) {
            Object newElement = function.apply(element());
            if (newElement != null) {
                newQueue.enqueue(newElement);
            }
            enqueue(dequeue());
        }
        return newQueue;
    }

    public Queue filter(Predicate<Object> predicate) {
        return map((Object element) -> {
            if (predicate.test(element)) {
                return element;
            } else {
                return null;
            }
        });
    }
}
