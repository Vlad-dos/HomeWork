import java.util.ArrayList;
import java.util.Random;

public class TestArrayQueueADT {
    static private ArrayList<Object> queue = new ArrayList<>();

    public static void main(String[] args) {
        ArrayQueueADT myQueue = new ArrayQueueADT();
        final Random random = new Random(10);

        for (int i = 0; i < 10431; i++) {
            ArrayQueueADT.enqueue(myQueue, i * 10000);
        }
        ArrayQueueADT.clear(myQueue);
        for (int i = 0; i < 31; i++) {
            ArrayQueueADT.enqueue(myQueue, i * 100);
        }
        while (!ArrayQueueADT.isEmpty(myQueue)) {
            System.out.println(ArrayQueueADT.size(myQueue) + " " + ArrayQueueADT.element(myQueue) + " " + ArrayQueueADT.dequeue(myQueue));
        }

        for (int i = 0; i < 1e6; i++) {
            int type = random.nextInt() % 3;
            if (type == 0) {
                int x = random.nextInt();
                ArrayQueueADT.enqueue(myQueue, x);
                queue.add(x);
            }
            if (!queue.isEmpty()) {
                if (type == 1) {
                    if (ArrayQueueADT.element(myQueue) != queue.get(0)) {
                        System.out.println("FAIL1");
                    }
                } else if (type == 2) {
                    if (ArrayQueueADT.dequeue(myQueue) != queue.get(0)) {
                        System.out.println("FAIL2");
                    }
                    queue.remove(0);
                }
            }
        }
    }
}
