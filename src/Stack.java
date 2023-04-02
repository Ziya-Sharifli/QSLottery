public class Stack {

    private int top;
    private Object[] elements;

    Stack(int capacity) {
        elements = new Object[capacity];
        top = -1;
    }
    void push(Object data) {
        if (isFull()) {
            System.out.println("Stack is full");
        }
        else {
            top++;
            elements[top] = data;
        }
    }
    Object pop() {
        if (isEmpty()) {
            //System.out.println("Stack is empty");
            return null;
        }
        else {
            Object retData = elements[top];
            top--;
            return retData;
        }
    }

    Object peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        else {
            return elements[top];
        }
    }
    boolean isEmpty() {
        return (top == -1);
    }

    boolean isFull() {
        return (top + 1 == elements.length);

    }
    int size() {
        return top+1;
    }

    public boolean contains(Object obj) {
        for (int i = 0; i <= top; i++) {
            if (elements[i].equals(obj)) {
                return true;
            }
        }
        return false;
    }

    public Stack copy() {
        Stack newStack = new Stack(elements.length);
        for (int i = 0; i <= top; i++) {
            newStack.push(elements[i]);
        }
        return newStack;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < top; i++) {
                sb.append(elements[i]).append(", ");
            }
            sb.append(elements[top]).append("]");
            return sb.toString();
        }
    }



}
