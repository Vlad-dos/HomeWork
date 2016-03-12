public class LinkedQueue extends AbstractQueue {
    private class Node {
        Object data;
        Node next;
        Node prev;

        Node(Object data, Node prev, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        Node() {
            data = null;
            next = null;
            prev = null;
        }
    }

    private Node head = new Node();
    private Node tail = head;

    protected void doEnqueue(Object a) {
        tail.next = new Node(a, tail, null);
        tail = tail.next;
    }

    protected Object doElement() {
        return head.next.data;
    }

    protected Object doDequeue() {
        head = head.next;
        return head.data;
    }

    protected void doClear() {
        head = new Node();
        tail = head;
    }
}
