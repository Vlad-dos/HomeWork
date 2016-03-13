public class ArrayQueueADT {
    static final private int MIN_SIZE = 10;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private Object[] data = new Object[MIN_SIZE];

    //pre: myqueue != null
    //post data.length > capacity
    static private void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity < queue.data.length) {
            return;
        }
        Object[] newData = new Object[2 * capacity];
        if (queue.tail > queue.head) {
            System.arraycopy(queue.data, queue.head, newData, 0, queue.tail - queue.head);
        } else {
            System.arraycopy(queue.data, queue.head, newData, 0, queue.data.length - queue.head);
            System.arraycopy(queue.data, 0, newData, queue.data.length - queue.head, queue.tail);
        }
        queue.data = newData;
        queue.head = 0;
        queue.tail = queue.size;
    }

    //pre: myqueue != null
    //post: result = next i
    static private int next(ArrayQueueADT queue, int i) {
        return (i + 1) % queue.data.length;
    }

    //pre: myqueue != null
    //post: result = prev i
    static private int prev(ArrayQueueADT queue, int i) {
        return (i + queue.data.length - 1) % queue.data.length;
    }

    // pre: a != null
    //      myqueue != null
    // post: size == size' + 1 &&
    //       Any 0 <= i < size - 1 data[i] == data'[i]
    //       data[size - 1] == a
    static public void enqueue(ArrayQueueADT queue, Object a) {
        ensureCapacity(queue, queue.size + 1);
        queue.size++;
        queue.data[queue.tail] = a;
        queue.tail = next(queue, queue.tail);
    }

    // pre: a != null
    //      myqueue != null
    // post: size == size' + 1 &&
    //       Any 0 < i < size data[i - 1] == data'[i]
    //       data[0] == a
    static public void push(ArrayQueueADT queue, Object a) {
        ensureCapacity(queue, queue.size + 1);
        queue.size++;
        queue.head = prev(queue, queue.head);
        queue.data[queue.head] = a;
    }

    // pre: size > 0
    //      myqueue != null
    // post: result == data[0]
    //       Any 0 <= i < size data[i] == data'[i]
    static public Object element(ArrayQueueADT queue) {
        return queue.data[queue.head];
    }

    // pre: size > 0
    //      myqueue != null
    // post: result == data[size - 1]
    //       Any 0 <= i < size data[i] == data'[i]
    static public Object peek(ArrayQueueADT queue) {
        return queue.data[prev(queue, queue.tail)];
    }

    // pre: size > 0
    //      myqueue != null
    // post: result == data[0]
    //       Any 0 <= i < size data[i + 1] == data'[i]
    static public Object dequeue(ArrayQueueADT queue) {
        queue.size--;
        Object tmp = queue.data[queue.head];
        queue.data[queue.head] = null;
        queue.head = next(queue, queue.head);
        return tmp;
    }

    // pre: size > 0
    //      myqueue != null
    // post: result == data[size - 1]
    //       Any 0 <= i < size - 1 data[i] == data'[i]
    static public Object remove(ArrayQueueADT queue) {
        queue.size--;
        queue.tail = prev(queue, queue.tail);
        Object tmp = queue.data[queue.tail];
        queue.data[queue.tail] = null;
        return tmp;
    }

    //pre: myqueue != null
    // post: result == size
    //       data == data';
    //       size = size'
    static public int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //pre: myqueue != null
    // post: result == size > 0
    //       data == data';
    //       size = size'
    static public boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    //pre: myqueue != null
    // post: size == 0
    //       data == data';
    static public void clear(ArrayQueueADT queue) {
        queue.tail = 0;
        queue.head = 0;
        queue.size = 0;
        queue.data = new Object[MIN_SIZE];
    }
}
