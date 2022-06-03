package ch.zhaw.it.prog2.functional.combinefunctions;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class PredicateDemo {

    public static void main(String[] args) {
        IntPredicate isEven = x -> x % 2 == 0;
        IntPredicate isDividableBy3 = x -> x % 3 == 0;
        IntPredicate isDividableBy6 = isEven.and(isDividableBy3);

        printTitle("Even");
        showFunctionValues(isEven);
        printTitle("Dividable by 6");
        showFunctionValues(isDividableBy6);
    }

    private static void printTitle(String title) {
        System.out.printf("======== %s%n", title);
    }

    private static void showFunctionValues(IntPredicate predicateFunction) {
        IntStream.rangeClosed(4, 12).forEach(i ->
            System.out.printf("%2d -> %5s%n", i, predicateFunction.test(i)));
    }
}
