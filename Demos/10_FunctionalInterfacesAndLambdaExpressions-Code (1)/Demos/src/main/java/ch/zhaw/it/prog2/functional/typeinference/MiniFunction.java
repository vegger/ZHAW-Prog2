package ch.zhaw.it.prog2.functional.typeinference;

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToLongFunction;
import java.util.stream.IntStream;

/**
 * Show the same lambda expression (x->x*x) applied to different functional interfaces
 */
public class MiniFunction {
    // this method is on slide 14
    public static void showIntSquares() {
        for (int i = 1; i < 4; i++) {
            IntToLongFunction intSquare = x -> (long) x * x;
            System.out.format("%2d => %3d%n", i, intSquare.applyAsLong(i));
        }
    }

    public static void showDoubleSquares() {
        for (int i = 1; i < 4; i++) {
            DoubleUnaryOperator doubleSquare = x -> x * x;
            System.out.format("%2d => %3f%n", i, doubleSquare.applyAsDouble(i));
        }
    }

    public static void showIntSquaresWithStream() {
        IntStream.range(1, 4).map(n -> n * n).forEach(System.out::println);
    }

    public static void main(String[] args) {
        System.out.println("========== IntToLongFunction");
        showIntSquares();
        System.out.println("========== DoubleUnaryOperator");
        showDoubleSquares();
        System.out.println("========== InStream with .map");
        showIntSquaresWithStream();
    }
}
