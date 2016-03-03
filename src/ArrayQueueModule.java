// inv : size >= 0 &&
//       data[head..tail] != null

public class ArrayQueueModule {
    static final private int MIN_SIZE = 10;
    static private int head = 0;
    static private int tail = 0;
    static private int size = 0;
    static private Object[] data = new Object[MIN_SIZE];

    static private void ensureCapacity(int capacity) {
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

    static private int next(int i) {
        return (i + 1) % data.length;
    }

    static private int prev(int i) {
        return (i + data.length - 1) % data.length;
    }

    // pre: a != null
    // post: size == size' + 1 &&
    //       tail == tail' + 1 &&
    //       data[tail'] == a
    static public void enqueue(Object a) {
        ensureCapacity(size + 1);
        size++;
        data[tail] = a;
        tail = next(tail);
    }

    // pre: a != null
    // post: size == size' + 1 &&
    //       head == head' - 1 &&
    //       data[head] == a
    static public void push(Object a) {
        ensureCapacity(size + 1);
        size++;
        head = prev(head);
        data[head] = a;
    }

    // pre: size > 0
    // post: result == data[head]
    static public Object element() {
        return data[head];
    }

    // pre: size > 0
    // post: result == data[tail - 1]
    static public Object peek() {
        return data[prev(tail)];
    }

    // pre: size > 0
    // post: result == data[head] && head = head' + 1
    static public Object dequeue() {
        size--;
        Object tmp = data[head];
        head = next(head);
        return tmp;
    }

    // pre: size > 0
    // post: result == data[tail] && tail = tail' - 1
    static public Object remove() {
        size--;
        tail = prev(tail);
        return data[tail];
    }

    // post: result == size
    static public int size() {
        return size;
    }

    // post: result == size > 0
    static public boolean isEmpty() {
        return size == 0;
    }

    // post: head = 0 && tail == 0 && size == 0
    static public void clear() {
        tail = 0;
        head = 0;
        size = 0;
        data = new Object[MIN_SIZE];
    }
}
