package ch.zhaw.it.prog2.functional.stream;

import java.util.Iterator;
import java.util.List;

public class InternalExternalIterator {

    public static void main(String[] args) {
        List<Integer> list = List.of(3, 2, 12, 5, 6, 11, 13);
        System.out.println("Source list: " + list);

        // using external iteration
        System.out.println("Using external iterator:");
        printEvenNumbersExternalIterator(list);

        System.out.println("Using external for-loop:");
        printEvenNumbersExternalForLoop(list);

        System.out.println("Using external for-each-loop:");
        printEvenNumbersExternalForEach(list);

        // using collection internal iteration
        System.out.println("Sum of even values (declarative):");
        printEvenNumbersInternalCollection(list);

        // using stream internal iteration
        System.out.println("Sum of even values (collectionIterator):");
        printEvenNumbersInternalStream(list);
    }

    static void printEvenNumbersExternalIterator(List<Integer> list) {
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int i = it.next();
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }

    static void printEvenNumbersExternalForLoop(List<Integer> list) {
        for (int index = 0; index < list.size(); index++) {
            int i = list.get(index);
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }

    static void printEvenNumbersExternalForEach(List<Integer> list) {
        for (Integer i: list) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }

    static void printEvenNumbersInternalCollection(List<Integer> list) {
        list.forEach(i -> {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        });
    }

    static void printEvenNumbersInternalStream(List<Integer> list) {
        list.stream()
            .filter(i -> i % 2 == 0)
            .forEach(System.out::println);
    }
}
