public class ArrayQueueADT {
    static final private int MIN_SIZE = 10;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private Object[] data = new Object[MIN_SIZE];

    static private void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity < queue.data.length) {
            return;
        }
        Object[] newData = new Object[2 * capacity];
        if (queue.tail > queue.head) {
            System.arraycopy(queue.data, queue.head, newData, 0, queue.tail - queue.head);
        } else {
            int j = 0;
            for (int i = queue.head; i < queue.data.length; i++) {
                newData[j++] = queue.data[i];
            }
            for (int i = 0; i < queue.tail; i++) {
                newData[j++] = queue.data[i];
            }
        }
        queue.data = newData;
        queue.head = 0;
        queue.tail = queue.size;
    }

    static private int next(ArrayQueueADT queue, int i) {
        return (i + 1) % queue.data.length;
    }

    // pre: a != null
    // post: size == size' + 1 &&
    //       tail == tail' + 1 &&
    //       data[tail'] == a
    static public void enqueue(ArrayQueueADT queue, Object a) {
        ensureCapacity(queue, queue.size + 1);
        queue.size++;
        queue.data[queue.tail] = a;
        queue.tail = next(queue, queue.tail);
    }

    // pre: size > 0
    // post: result == data[head]
    static public Object element(ArrayQueueADT queue) {
        return queue.data[queue.head];
    }

    // pre: size > 0
    // post: result == data[head] && head = head' + 1
    static public Object dequeue(ArrayQueueADT queue) {
        queue.size--;
        Object tmp = queue.data[queue.head];
        queue.head = next(queue, queue.head);
        return tmp;
    }

    // post: result == size
    static public int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // post: result == size > 0
    static public boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // post: head = 0 && tail == 0 && size == 0
    static public void clear(ArrayQueueADT queue) {
        queue.tail = 0;
        queue.head = 0;
        queue.size = 0;
        queue.data = new Object[MIN_SIZE];
    }
}
