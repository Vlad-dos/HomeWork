public class ArrayQueue extends AbstractQueue {
    final private int MIN_SIZE = 10;
    private int head = 0;
    private int tail = 0;
    private Object[] data = new Object[MIN_SIZE];

    private void ensureCapacity(int capacity) {
        if (capacity < data.length) {
            return;
        }
        Object[] newData = new Object[2 * capacity];
        if (tail > head) {
            System.arraycopy(data, head, newData, 0, tail - head);
        } else {
            System.arraycopy(data, head, newData, 0, data.length - head);
            System.arraycopy(data, 0, newData, data.length - head, tail);
        }
        data = newData;
        head = 0;
        tail = size;
    }

    protected int next(int i) {
        return (i + 1) % data.length;
    }

    protected void doEnqueue(Object a) {
        ensureCapacity(size + 1);
        data[tail] = a;
        tail = next(tail);
    }

    protected Object doElement() {
        return data[head];
    }

    protected Object doDequeue() {
        Object tmp = data[head];
        data[head] = null;
        head = next(head);
        return tmp;
    }

    protected void doClear() {
        tail = 0;
        head = 0;
        data = new Object[MIN_SIZE];
    }

    protected Queue makeQueue() {
        return new ArrayQueue();
    }
}

