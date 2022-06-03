package ch.zhaw.it.prog2.functional.stream;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class TerminalOperations {
    private static final Predicate<Integer> isEven = x -> x % 2 == 0;

    public static void main(String[] args) {
        List<Integer> integerList = List.of(3, 6, 2, 5, 6, 12, 11, 13);

        // slides terminal operations
        printObject("Average of square of even numbers:",
            List.of(3, 6, 2, 5, 6, 12, 11, 13).stream()
                .filter(isEven)
                .mapToInt(Integer::intValue)
                .map(i -> i * i)
                .average()
        );

        printObject("Lowest element > 4:",
            List.of(3, 6, 2, 5, 6, 12, 11, 13).stream()
                .filter(i -> i > 4)
                .sorted()
                .findFirst()
        );

        // matching
        printOptional("First element > 5:",
            integerList.stream()
                .filter(i -> i > 5)
                .findFirst()
        );

        printOptional("Any element > 5:",
            integerList.stream()
                .filter(i -> i > 5)
                .findAny()
        );

        printObject("All elements < 13:",
            integerList.stream()
                .allMatch(i -> i < 13)
        );

        printObject("Any elements > 10:",
            integerList.stream()
                .anyMatch(i -> i > 10)
        );

        printObject("No elements < 0:",
            integerList.stream()
                .noneMatch(i -> i < 0)
        );

        // aggregate results
        printObject("Number of elements < 10:",
            integerList.stream()
                .filter(i -> i < 10)
                .count()
        );

        printObject("Max value Stream<Integer>:",
            integerList.stream()
                .max(Integer::compareTo)
        );

        printObject("Max value IntStream:",
            integerList.stream()
                .mapToInt(Integer::intValue)
                .max()
        );


        printObject("Min value Stream<Integer>:",
            integerList.stream()
                .min(Integer::compareTo)
        );

        printObject("Min value IntStream:",
            integerList.stream()
                .mapToInt(Integer::intValue)
                .min()
        );

        printObject("Average IntStream:",
            integerList.stream()
                .mapToInt(Integer::intValue)
                .average()
        );

        printObject("Sum IntStream:",
            integerList.stream()
                .mapToInt(Integer::intValue)
                .sum()
        );

        printObject("Summary Statistics IntStream:",
            integerList.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics()
        );

        // reduce
        printOptional("Sum using reduce:",
            integerList.stream()
                .reduce((sum, nextValue) -> sum + nextValue)); // optionally use Integer::sum

        printOptional("Sum of squares using reduce:",
            integerList.stream()
                .reduce((sum, nextValue) -> sum + nextValue * nextValue));

        printObject("Sum using using identity value 0 and accumulator:",
            integerList.stream()
                .reduce(0, (sum, nextValue) -> sum + nextValue)); // optionally use Integer::sum

        printObject("Sum using identity value 0, accumulator and combiner:",
            integerList.stream()
                .reduce(0, (sum, nextValue) -> sum + nextValue, (sum1, sum2) -> sum1 + sum2)); // optionally use Integer::sum

        // show details of reduce parallel stream using accumulator and combiner
        reduceAggregateCombinerDetails(integerList);

        // collectors
        printObject("Collect odd numbers as array:",
            integerList.stream()
                .filter(Predicate.not(isEven))
                .toArray(Integer[]::new)
        );

        printObject("Collect even numbers as list:",
            integerList.stream()
                .filter(isEven)
                .toList()
        );

        printObject("Group even/odd numbers:",
            integerList.stream()
                .collect(Collectors.groupingBy(i -> i % 2 == 0 ? "even":"odd"))
        );

        printObject("Create squareValue Map:",
            integerList.stream()
                .distinct()
                .collect(Collectors.toMap(i -> i, i -> i * i))
        );

        printObject("Joining values to CSV String:",
            integerList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";", "[", "]"))
        );

    }

    static void reduceAggregateCombinerDetails(List<Integer> list) {
        System.out.println("---");
        int sumAggregateWithCombiner =
            list.parallelStream()    // show difference to list.stream()
            .reduce(0,
                (sum, nextValue) -> {
                    System.out.format("accumulator: sum=%s; nextValue=%s (%s)%n", sum, nextValue, Thread.currentThread().getName());
                    return sum + nextValue;
                },
                (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s  (%s)%n", sum1, sum2, Thread.currentThread().getName());
                    return sum1 + sum2;
                });
        printObject("Sum using identity 0, accumulator and combiner:", sumAggregateWithCombiner);
    }


    static void printObject(String prompt, Object object) {
        System.out.printf("%s %s%n", prompt, object);
    }

    static void printOptional(String prompt, OptionalInt optional) {
        System.out.format("%s %s", prompt, optional.isPresent() ? optional.getAsInt() : "<noValue>");
    }

    static void printOptional(String prompt, Optional<Integer> optional) {
        System.out.format("%s %s", prompt, optional.isPresent() ? optional.get() : "<noValue>");
    }

    static void printStreamContent(String prompt, Stream<?> stream) {
        System.out.print(prompt + " ");
        System.out.println(stream.map(String::valueOf).collect(Collectors.joining(",", "[", "]")));
    }

    static void printStreamContent(String prompt, IntStream stream) {
        printStreamContent(prompt, stream.mapToObj(Integer::valueOf));
    }

    static void printStreamContent(String prompt, LongStream stream) {
        printStreamContent(prompt, stream.mapToObj(Long::valueOf));
    }

    static void printStreamContent(String prompt, DoubleStream stream) {
        printStreamContent(prompt, stream.mapToObj(Double::valueOf));
    }
}
