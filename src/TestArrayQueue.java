import java.util.ArrayList;
import java.util.Random;

public class TestArrayQueue {
    static private ArrayList<Object> queue = new ArrayList<>();

    public static void main(String[] args) {
        ArrayQueueADT myQueue = new ArrayQueueADT();
        ArrayQueue myClassQueue = new ArrayQueue();
        final Random random = new Random(10);

        for (int i = 0; i < 10431; i++) {
            ArrayQueueADT.enqueue(myQueue, i * 10000);
            ArrayQueueModule.enqueue(i * 10000);
            myClassQueue.enqueue(i * 10000);
        }
        ArrayQueueADT.clear(myQueue);
        ArrayQueueModule.clear();
        myClassQueue.clear();
        for (int i = 0; i < 31; i++) {
            ArrayQueueModule.enqueue(i * 100);
            ArrayQueueADT.enqueue(myQueue, i * 100);
            myClassQueue.enqueue(i * 100);
        }
        while (!ArrayQueueADT.isEmpty(myQueue)) {
            System.out.println(ArrayQueueADT.size(myQueue) + " " + ArrayQueueADT.element(myQueue) + " " + ArrayQueueADT.dequeue(myQueue));
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
            System.out.println(myClassQueue.size() + " " + myClassQueue.element() + " " + myClassQueue.dequeue());
        }

        for (int i = 0; i < 1e6; i++) {
            int type = random.nextInt() % 3;
            if (type == 0) {
                int x = random.nextInt();
                ArrayQueueADT.enqueue(myQueue, x);
                queue.add(x);
                ArrayQueueModule.enqueue(x);
                myClassQueue.enqueue(x);
            }
            if (!queue.isEmpty()) {
                if (type == 1) {
                    if (!ArrayQueueADT.element(myQueue).toString().equals(queue.get(0).toString())) {
                        System.out.println("FAIL1 in ADT");
                    }
                    if (!ArrayQueueModule.element().toString().equals(queue.get(0).toString())) {
                        System.out.println("FAIL1 in Module");
                    }
                    if (!myClassQueue.element().toString().equals(queue.get(0).toString())) {
                        System.out.println("FAIL1 in Class");
                    }
                } else if (type == 2) {
                    if (!ArrayQueueADT.dequeue(myQueue).toString().equals(queue.get(0).toString())) {
                        System.out.println("FAIL2 in ADT");
                    }
                    if (!ArrayQueueModule.dequeue().toString().equals(queue.get(0).toString())) {
                        System.out.println("FAIL2 in Module");
                    }
                    if (!myClassQueue.dequeue().toString().equals(queue.get(0).toString())) {
                        System.out.println("FAIL2 in Class");
                    }
                    queue.remove(0);
                }
            }
        }
    }
}
