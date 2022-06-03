package ch.zhaw.it.prog2.functional.combinefunctions;

import java.util.function.DoubleUnaryOperator;

public class PointToGrade {
    public static final DoubleUnaryOperator addOne = x -> x + 1;
    public static final DoubleUnaryOperator divFour = x -> x / 4;

    private static void print(double value) {
        System.out.format("%2.2f%n", value);
    }

    public static void main(String[] args) {
        DoubleUnaryOperator pointToGrade;
        double points = 13;

        print(addOne.applyAsDouble(divFour.applyAsDouble(points)));

        pointToGrade = divFour.andThen(addOne);
        print(pointToGrade.applyAsDouble(points));

        pointToGrade = addOne.compose(divFour);
        print(pointToGrade.applyAsDouble(points));

        pointToGrade = divFour.andThen(divFour).andThen(addOne);
        print(pointToGrade.applyAsDouble(points));
    }
}
