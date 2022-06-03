package ch.zhaw.it.prog2.functional.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class IntermediateOperations {
    private static final Predicate<Integer> isEven = x -> x % 2 == 0;


    public static void main(String[] args) {
        // slides
        List<Integer> integerList = List.of(3, 6, 2, 5, 6, 12, 11, 13);
        List<List<Integer>> listOfList = List.of(List.of(2,3,6,5), List.of(12,13,11));

        System.out.println("Source IntegerList: " + integerList);
        System.out.println("Source ListOfList: " + listOfList);

        // Intermediate demo slide
        printStreamContent("Skip 2, even, square, sorted",
            List.of(3, 6, 6, 5, 2, 12, 11, 13).stream()
                .skip(2)
                .filter(isEven)
                .map(i -> i * i)
                .sorted()
        );

        try (Stream<Path> fileStream = Files.list(Paths.get("."))) {
            printStreamContent("List Directories:",
                fileStream
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // flatMap slide
        printStreamContent("FlatMap result:",
            Stream.of(List.of(2,3,6,5), List.of(12,13,11))   // Stream<List<Integer>>
                .flatMap(List::stream)                       // Stream<Integer>
        );


        // other examples

        printStreamContent("Distinct:",
            integerList.stream()
                .distinct()
        );

        printStreamContent("Skip 2 / Limit 4:",
            integerList.stream()
                .skip(2)
                .limit(4)
        );

        printStreamContent("takeWhile < 10:",
            integerList.stream()
            .takeWhile(i -> i < 10)
        );

        printStreamContent("dropWhile < 10:",
            integerList.stream()
                .dropWhile(i -> i < 10)
        );

        printStreamContent("sorted, dropWhile < 6:",
            integerList.stream()
                .sorted()
                .dropWhile(i -> i < 6)
        );

        printStreamContent("filter even numbers, sort reverse:",
            integerList.stream()
                .filter(i -> i % 2 == 0)
                .sorted(Comparator.reverseOrder())
        );

        printStreamContent("squares of even numbers:",
            integerList.stream()
            .filter(i -> i % 2 == 0)
            .map(i -> i * i)
        );

        printStreamContent("FlatMap:",
            listOfList.stream()
                .flatMap(List::stream)
        );

        printStreamContent("FlatMap sort reverse:",
            listOfList.stream()
                .flatMap(List::stream)
                .sorted(Comparator.reverseOrder())
        );

        try (Stream<Path> fileStream = Files.list(Paths.get("."))) {
            printStreamContent("List Directories:",
                fileStream
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    static void printStreamContent(String prompt, Stream stream) {
        System.out.print(prompt + " ");
        System.out.println(stream.map(String::valueOf).collect(Collectors.joining(",", "[", "]")));
    }

    static void printStreamContent(String prompt, IntStream stream) {
        printStreamContent(prompt, stream.boxed());
    }

    static void printStreamContent(String prompt, LongStream stream) {
        printStreamContent(prompt, stream.boxed());
    }

    static void printStreamContent(String prompt, DoubleStream stream) {
        printStreamContent(prompt, stream.boxed());
    }


}
