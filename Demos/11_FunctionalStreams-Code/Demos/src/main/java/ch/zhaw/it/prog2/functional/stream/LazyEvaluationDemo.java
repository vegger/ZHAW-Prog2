package ch.zhaw.it.prog2.functional.stream;

import java.util.List;

public class LazyEvaluationDemo {
    public static void main(String[] args) {
        List<Integer> integerList = List.of(3, 2, 12, 5, 6, 13, 11);
        lazyEvaluationReduce(integerList);
        lazyEvaluationReduceTakeWhile(integerList);
        lazyEvaluationFindAny(integerList);
    }


    static void lazyEvaluationReduce(List<Integer> integerList) {
        System.out.println("Evaluate List (reduce): " + integerList);
        System.out.println("Result: " +
            integerList.stream()
                .peek(i -> System.out.format("filter: %d%n", i))
                .filter(i -> i % 2 == 0)
                .map(i -> {
                    int result = i * i;
                    System.out.format("map: %d -> %d%n", i, result);
                    return result;
                })
                .reduce((sum, nextValue) -> {
                    int result = sum + nextValue;
                    System.out.format("reduce: aggregate=%d next=%d -> %d%n", sum, nextValue, result);
                    return result;
                })
        );
        System.out.println();
    }

    static void lazyEvaluationReduceTakeWhile(List<Integer> integerList) {
        System.out.println("Evaluate List (takeWhile): " + integerList);
        System.out.println("Result: " +
            integerList.stream()
                .peek(i -> System.out.format("takeWhile: %d%n", i))
                .takeWhile(i -> i < 13)
                .peek(i -> System.out.format("filter: %d%n", i))
                .filter(i -> i % 2 == 0)
                .map(i -> {
                    int result = i * i;
                    System.out.format("map: %d -> %d%n", i, result);
                    return result;
                })
                .reduce((sum, nextValue) -> {
                    int result = sum + nextValue;
                    System.out.format("reduce: aggregate=%d next=%d -> %d%n", sum, nextValue, result);
                    return result;
                })
        );
        System.out.println();
    }

    static void lazyEvaluationFindAny(List<Integer> integerList) {
        System.out.println("Evaluate List (findAny): " + integerList);
        System.out.println("Result: " +
            integerList.stream()
                .peek(i -> System.out.format("takeWhile: %d%n", i))
                .takeWhile(i -> i < 13)
                .peek(i -> System.out.format("filter: %d%n", i))
                .filter(i -> i % 2 == 0)
                .map(i -> {
                    int result = i * i;
                    System.out.format("map: %d -> %d%n", i, result);
                    return result;
                })
                .findAny()
        );
        System.out.println();
    }

}
