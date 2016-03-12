package queue;// inv : size >= 0 &&
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
            System.arraycopy(data, head, newData, 0, data.length - head);
            System.arraycopy(data, 0, newData, data.length - head, tail);
        }
        data = newData;
        head = 0;
        tail = size;
    }

    //post: result = next i
    static private int next(int i) {
        return (i + 1) % data.length;
    }

    //post: result = prev i
    static private int prev(int i) {
        return (i + data.length - 1) % data.length;
    }

    // pre: a != null
    // post: size == size' + 1 &&
    //       Any 0 <= i < size - 1 data[i] == data'[i]
    //       data[size - 1] == a
    static public void enqueue(Object a) {
        ensureCapacity(size + 1);
        size++;
        data[tail] = a;
        tail = next(tail);
    }

    // pre: a != null
    // post: size == size' + 1 &&
    //       Any 0 < i < size data[i - 1] == data'[i]
    //       data[0] == a
    static public void push(Object a) {
        ensureCapacity(size + 1);
        size++;
        head = prev(head);
        data[head] = a;
    }

    // pre: size > 0
    // post: result == data[0]
    //       Any 0 <= i < size data[i] == data'[i]
    static public Object element() {
        return data[head];
    }

    // pre: size > 0
    // post: result == data[size - 1]
    //       Any 0 <= i < size data[i] == data'[i]
    static public Object peek() {
        return data[prev(tail)];
    }

    // pre: size > 0
    // post: result == data[0]
    //       Any 0 <= i < size data[i + 1] == data'[i]
    static public Object dequeue() {
        size--;
        Object tmp = data[head];
        data[head] = null;
        head = next(head);
        return tmp;
    }

    // pre: size > 0
    // post: result == data'[size - 1]
    //       Any 0 <= i < size - 1 data[i] == data'[i]
    static public Object remove() {
        size--;
        tail = prev(tail);
        Object tmp = data[tail];
        data[tail] = null;
        return tmp;
    }

    // post: result == size
    //       data == data';
    //       size == size'
    static public int size() {
        return size;
    }

    // post: result == size > 0
    //       data == data'
    //       size == size'
    static public boolean isEmpty() {
        return size == 0;
    }

    // post: size == 0
    //       data == data';
    static public void clear() {
        tail = 0;
        head = 0;
        size = 0;
        data = new Object[MIN_SIZE];
    }
}
