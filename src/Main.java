import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        CircularQueue q1 = new CircularQueue(13);
        CircularQueue q2 = new CircularQueue(13);

        String[] table;
        Scanner keyboard = new Scanner(System.in);

        try {
            table = Files.readAllLines(Paths.get("highscoretable.txt")).toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < table.length; i++) { // read the file into an array, then split it and enqueue to 2 separate queues.

            String[] arrOfTable = table[i].split(" ");
            q1.enqueue(arrOfTable[1]);
            q2.enqueue(arrOfTable[0]);

        }


        System.out.println("\nPlease enter n: ");
        int n = keyboard.nextInt();

        if (n<7 || n>10) {
            System.out.println("n must be between 7 and 10.");
            System.exit(0);
        }

        Stack s1 = new Stack(n);
        Stack s2 = new Stack(n);

        String stackValues = "A2345678910JQK";
        String[] arrOfValues = stackValues.split("");

        Random random = new Random();

        Set<Character> selectedValues = new HashSet<>();

        while (selectedValues.size() < n) { // Unique values for s1
            int index = random.nextInt(stackValues.length());
            char value = stackValues.charAt(index);
            if (!selectedValues.contains(value)) {
                selectedValues.add(value);
                s1.push(value);
            }
        }
        selectedValues = new HashSet<>();

        while (selectedValues.size() < n) { // Unique sets for s2
            int index = random.nextInt(stackValues.length());
            char value = stackValues.charAt(index);
            if (!selectedValues.contains(value)) {
                selectedValues.add(value);
                s2.push(value);
            }
        }


        System.out.println("\nStack 1: " + s1);
        System.out.println("Stack 2: " + s2);

        Queue q3 = new Queue(500);
        Queue q4 = new Queue(500);


        for (int i = 0; i <= 13; i++) {
            q3.enqueue(arrOfValues[i]);

        }

        System.out.println("\nQueue 3: " + q3);

        Queue returnVal = new Queue(1);

        int point1 = 0;
        int point2 = 0;
        String nameInput = null;

        while (true) {

            bags(q3, q4, returnVal);
            System.out.println("Selected value: " + returnVal.peek());
            String returnValue = (String) returnVal.peek();
            char returnValueChar = returnValue.charAt(0);
            Stack s1copy = s1.copy();
            Stack s1copycopy = new Stack(500);


            int s1size = s1.size();
            int s1copysize = s1.size();


            for (int i = 0; i < s1size; i++) {

                if (s1.contains(returnValueChar)) {
                    char curr = (char) s1.peek();
                    s1copycopy.push(curr);
                    s1.pop();
                    point1 = point1 + 10;
                } else {
                    break;
                }

            }

            s1copycopy.pop();

            int sizecopycopy = s1copycopy.size();

            for (int i = 0; i < sizecopycopy; i++) {

                char curr = (char) s1copycopy.peek();
                s1.push(curr);
                s1copycopy.pop();

            }

            System.out.println("Player 1: " + s1);

            // stack 2


            Stack s2copy = s2.copy();
            Stack s2copycopy = new Stack(500);

            int s2size = s2.size();
            int s2copysize = s2.size();


            for (int i = 0; i < s2size; i++) {

                if (s2.contains(returnValueChar)) {
                    char curr = (char) s2.peek();
                    s2copycopy.push(curr);
                    s2.pop();
                    point2 = point2 + 10;
                } else {
                    break;
                }

            }
            s2copycopy.pop();

            int size2copycopy = s2copycopy.size();

            for (int i = 0; i < size2copycopy; i++) {

                char curr = (char) s2copycopy.peek();
                s2.push(curr);
                s2copycopy.pop();

            }

            returnVal = new Queue(1);
            System.out.println("Player 2: " + s2);


            if (s1.isEmpty()) {
                System.out.println("Player 1 won! Score: " + point1);
                System.out.print("\nWhat is your name? \n-> ");
                nameInput = keyboard.next();
                nameInput = nameInput.substring(0, 1).toUpperCase() + nameInput.substring(1);
                q1.enqueue(point1);
                q2.enqueue(nameInput);
                break;
            }

            if (s2.isEmpty()) {
                System.out.println("Player 2 won! Score: " + point2);
                System.out.print("\nWhat is your name? \n-> ");
                nameInput = keyboard.next();
                nameInput = nameInput.substring(0, 1).toUpperCase() + nameInput.substring(1);
                q1.enqueue(point2);
                q2.enqueue(nameInput);
                break;
            }

            if (s1.isEmpty() && s2.isEmpty()) {
                System.out.println("\nBoth players finished at the same time! This is a tie!");
                System.exit(0);
            }
            if (s1.size() == 1 && s2.size() == 1 && s1.peek() == s2.peek()) {
                System.out.println("\nBoth players finished at the same time! This is a tie!");
                System.exit(0);
            }


        }

        if (q1.isFull()) {

            System.out.println("\nCan not add another person to the high score table. The queue is full! (12 person max!)");
            System.exit(0);
        }

        sortQueueDescTable(q1, q2);

        PrintWriter pw = null;

        try {
            pw = new PrintWriter("highscoretable.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            PrintWriter out = new PrintWriter(new FileWriter("highscoretable.txt"));

            while (!q1.isEmpty()){

                String currentElementq1 = String.valueOf(q1.peek());
                String currentElementq2 = (String) q2.peek();

                out.println(currentElementq2 + " "+currentElementq1);
                q1.dequeue();
                q2.dequeue();
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] highscoretable;
        try {
            highscoretable = Files.readAllLines(Paths.get("highscoretable.txt")).toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n--------Table--------");
        System.out.println("Name -> Score");
        for (int i = 0; i < highscoretable.length ; i++) {

            System.out.println(highscoretable[i]);
        }

        System.out.println("\n*** highscoretable.txt has been sorted and updated ***");



    }

    public static void bags(Queue queue, Queue queue4, Queue returnValue) {

        Random random = new Random();
        Queue queue2 = new Queue(500);
        //System.out.println("\nQ3 (bag1): "+queue);

        int randomNumber = random.nextInt(queue.size());
        boolean flag = randomNumber == 0;
        while (flag) {
            randomNumber = random.nextInt(queue.size());
            flag = randomNumber == 0;
        }

        for (int i = 0; i < randomNumber; i++) {

            queue2.enqueue(queue.peek());
            queue.dequeue();

        }
        int q3copysize = queue2.size();
        Queue q3copycopy = new Queue(500);

        for (int i = 0; i < q3copysize - 1; i++) {
            q3copycopy.enqueue(queue2.peek());
            queue2.dequeue();
        }
        int q3copycopysize = q3copycopy.size();
        for (int i = 0; i < q3copycopysize; i++) {
            String q3copyelements = (String) q3copycopy.peek();
            q3copycopy.dequeue();
            queue.enqueue(q3copyelements);

        }
        System.out.println("\nQ3 (bag1): " + queue);
        returnValue.enqueue(queue2.peek());

        queue4.enqueue(queue2.peek());
        System.out.println("Q4 (bag2): " + queue4+"\n");


    }


    public static void sortQueueDescTable(CircularQueue queue, CircularQueue queue2) { // Sorting the queue for highscoretable.txt

        int number_of_elements = queue.size();
        CircularQueue tempQueue = new CircularQueue(number_of_elements);
        CircularQueue copyQueue = new CircularQueue(number_of_elements);

        CircularQueue tempQueue2 = new CircularQueue(number_of_elements);
        CircularQueue copyQueue2 = new CircularQueue(number_of_elements);


        // make a copy of the original queue
        while (!queue.isEmpty()) {
            copyQueue.enqueue(queue.dequeue());
            copyQueue2.enqueue(queue2.dequeue());
        }

        // iterate over the copy of the original queue
        while (!copyQueue.isEmpty()) {
            number_of_elements = copyQueue.size();
            int min = 999999;

            for (int i = 0; i < number_of_elements; i++) {
                int current = Integer.parseInt(String.valueOf(copyQueue.peek()));
                String currentString = (String) copyQueue2.peek();
                if (current < min) {
                    min = current;
                }
                copyQueue.enqueue(copyQueue.dequeue());
                copyQueue2.enqueue(copyQueue2.dequeue());
            }

            for (int i = 0; i < number_of_elements; i++) {
                int current = Integer.parseInt(String.valueOf(copyQueue.peek()));
                String currentString = (String) copyQueue2.peek();
                if (current == min) {
                    copyQueue.dequeue();
                    tempQueue.enqueue(min);

                    copyQueue2.dequeue();
                    tempQueue2.enqueue(currentString);
                } else {
                    copyQueue.enqueue(copyQueue.dequeue());
                    copyQueue2.enqueue(copyQueue2.dequeue());
                }
            }
        }

        // copy the sorted elements from the temporary queue back to the original queue
        while (!tempQueue.isEmpty()) {
            queue.enqueue(tempQueue.dequeue());
            queue2.enqueue(tempQueue2.dequeue());
        }

        Stack stack = new Stack(500); // creating stacks and pushing back the queue to them to achieve descending order
        Stack stackS = new Stack(500);

        while (!queue.isEmpty()) {
            stack.push(queue.dequeue());
            stackS.push(queue2.dequeue());
        }


        while (!stack.isEmpty()) {
            queue.enqueue(stack.pop());
            queue2.enqueue(stackS.pop());
        }


    }


}



