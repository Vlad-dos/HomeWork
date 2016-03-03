public class ArrayQueue {
    static final private int MIN_SIZE = 10;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private Object[] data = new Object[MIN_SIZE];

    private void ensureCapacity(int capacity) {
        if (capacity < data.length) {
            return;
        }
        Object[] newData = new Object[2 * capacity];
        if (tail > head) {
            System.arraycopy(data, head, newData, 0, tail - head);
        } else {
            int j = 0;
            for (int i = head; i < data.length; i++) {
                newData[j++] = data[i];
            }
            for (int i = 0; i < tail; i++) {
                newData[j++] = data[i];
            }
        }
        data = newData;
        head = 0;
        tail = size;
    }

    private int next(int i) {
        return (i + 1) % data.length;
    }

    // pre: a != null
    // post: size == size' + 1 &&
    //       tail == tail' + 1 &&
    //       data[tail'] == a
    public void enqueue(Object a) {
        ensureCapacity(size + 1);
        size++;
        data[tail] = a;
        tail = next(tail);
    }

    // pre: size > 0
    // post: result == data[head]
    public Object element() {
        return data[head];
    }

    // pre: size > 0
    // post: result == data[head] && head = head' + 1
    public Object dequeue() {
        size--;
        Object tmp = data[head];
        head = next(head);
        return tmp;
    }

    // post: result == size
    public int size() {
        return size;
    }

    // post: result == size > 0
    public boolean isEmpty() {
        return size == 0;
    }

    // post: head = 0 && tail == 0 && size == 0
    public void clear() {
        tail = 0;
        head = 0;
        size = 0;
        data = new Object[MIN_SIZE];
    }
}
