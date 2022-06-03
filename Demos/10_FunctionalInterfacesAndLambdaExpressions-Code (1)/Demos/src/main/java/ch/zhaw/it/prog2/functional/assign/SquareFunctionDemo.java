package ch.zhaw.it.prog2.functional.assign;

import java.util.function.IntToLongFunction;

/**
 * Simple demo of a function calculating squares.
 * Used for PROG2 slides in "11 - Functional Programming", on slide 19
 */
public class SquareFunctionDemo {
    private static final IntToLongFunction square = x -> x * x;

    public static void showSquares(int fromInclusive, int toInclusive) {
        for (int i = fromInclusive; i <= toInclusive; i++) {
            long squareResult = square.applyAsLong(i);
            System.out.format("%2d^2 = %4d%n", i, squareResult);
        }
    }

    public static void showLongFunction(IntToLongFunction longFunction, int fromInclusive, int toInclusive) {
        for (int i = fromInclusive; i <= toInclusive; i++) {
            long squareResult = longFunction.applyAsLong(i);
            System.out.format("%2d^2 = %4d%n", i, squareResult);
        }
    }

    public static void main(String[] args) {
        showSquares(1, 4);
        System.out.println("=============");
        showLongFunction(square, 8, 11);
    }
}
