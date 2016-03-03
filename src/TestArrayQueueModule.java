import java.util.ArrayList;
import java.util.Random;

public class TestArrayQueueModule {
    static private ArrayList<Object> queue = new ArrayList<>();

    public static void main(String[] args) {
        final Random random = new Random(10);

        for (int i = 0; i < 10431; i++) {
            ArrayQueueModule.enqueue(i * 10000);
        }
        ArrayQueueModule.clear();
        for (int i = 0; i < 31; i++) {
            ArrayQueueModule.enqueue(i * 100);
        }
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        }

        for (int i = 0; i < 1e6; i++) {
            int type = random.nextInt() % 3;
            if (type == 0) {
                int x = random.nextInt();
                ArrayQueueModule.enqueue(x);
                queue.add(x);
            }
            if (!queue.isEmpty()) {
                if (type == 1) {
                    if (ArrayQueueModule.element() != queue.get(0)) {
                        System.out.println("FAIL1");
                    }
                } else if (type == 2) {
                    if (ArrayQueueModule.dequeue() != queue.get(0)) {
                        System.out.println("FAIL2");
                    }
                    queue.remove(0);
                }
            }
        }
    }
}
