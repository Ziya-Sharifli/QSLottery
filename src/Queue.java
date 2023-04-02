public class Queue {

    private int rear;
    private int front;
    private Object[] elements;

    Queue(int capacity) {
        elements = new Object[capacity];
        rear = -1;
        front = 0;
    }

    void enqueue(Object data) {
        if (isFull()) {
            System.out.println("Queue is full");
        } else {
            rear++;
            elements[rear] = data;
        }
    }

    Object dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        } else {
            Object retData = elements[front];
            elements[front] = null;
            front++;
            return retData;
        }
    }

    Object peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        } else {
            return elements[front];
        }
    }

    boolean isEmpty() {
        return rear < front;
    }

    boolean isFull() {
        return (rear + 1 == elements.length);
    }

    int size() {
        return rear - front + 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = front; i <= rear; i++) {
            sb.append(elements[i]);
            if (i != rear) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean contains(Object obj) {
        for (int i = front; i <= rear; i++) {
            if (elements[i].equals(obj)) {
                return true;
            }
        }
        return false;
    }


}