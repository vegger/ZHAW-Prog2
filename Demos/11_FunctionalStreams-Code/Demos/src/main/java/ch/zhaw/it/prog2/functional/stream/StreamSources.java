package ch.zhaw.it.prog2.functional.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.*;

public class StreamSources {

    public static void main(String[] args) {
        // slides
        List<Integer> integerlist = List.of(3, 2, 12, 5, 6, 11, 13);
        Stream<Integer> integerListStream = integerlist.stream(); // create stream from collection
        integerListStream.forEach(System.out::println);    // processes and prints stream content

        int[] intsArray = {3,2,12,5,6,11,13};
        IntStream intsArrayStream = Arrays.stream(intsArray);
        intsArrayStream.forEach(System.out::println);

        // Collections
        List<Integer> list = List.of(3, 2, 12, 5, 6, 11, 13);
        Stream<Integer> listStream = list.stream();
        printStreamContent("Stream of list:", listStream);

        Collection<Integer> collection = Set.of(3, 2, 12, 5, 6, 11, 13);
        Stream<Integer> collectionStream = collection.stream();
        printStreamContent("Stream of set:", collectionStream);

        // Arrays
        Integer[] integerArray = {3,2,12,5,6,11,13};
        Stream<Integer> integerArrayStream = Arrays.stream(integerArray);
        printStreamContent("Stream of integerArray:", integerArrayStream);

        int[] intArray = {3,2,12,5,6,11,13};
        IntStream intArrayStream = Arrays.stream(intArray);
        printStreamContent("Stream of intArray:", intArrayStream);

        double[] doubleArray = {3,2,12,5,6,11,13};
        DoubleStream doubleArrayStream = Arrays.stream(doubleArray);
        printStreamContent("Stream of doubleArray:", doubleArrayStream);

        // generator methods
        Stream<Integer> integerStream = Stream.of(3,2,12,5,6,11,13);
        printStreamContent("Stream of Integer values:", integerStream);

        IntStream intValues = IntStream.of(3,2,12,5,6,11,13);
        printStreamContent("Stream of int values:", intValues);

        IntStream intRangeExcl = IntStream.range(1,10);
        printStreamContent("Stream of int range exlusive:", intRangeExcl);

        LongStream longRangeIncl = LongStream.rangeClosed(1,10);
        printStreamContent("Stream of long range inclusive:", longRangeIncl);

        DoubleStream doubleGeneratedStream = DoubleStream.generate(Math::random);
        printStreamContent("Stream of double random values:", doubleGeneratedStream.limit(10));

        DoubleStream doubleIterateStream = DoubleStream.iterate(3.0, x -> x + x/2);
        printStreamContent("Stream of double iterated values:", doubleIterateStream.limit(10));

        DoubleStream doubleIteratePredicateStream = DoubleStream.iterate(3.0, x-> x < 80, x -> x + x/2);
        printStreamContent("Stream of double iterated values:", doubleIteratePredicateStream);

        // String
        IntStream streamOfChars = "abc".chars(); // IntStream because there is noc CharStream class
        printStreamContent("Stream of string chars:", streamOfChars);

        Stream<String> stringElements = Pattern.compile(",").splitAsStream("a,b,c");
        printStreamContent("Stream of string elements:", stringElements);

        // Random
        Random random = new Random();
        DoubleStream doubleRandomStream = random.doubles(5);
        printStreamContent("Stream of 5 random values:", doubleRandomStream);

        DoubleStream doubleRandomBoundedStream = random.doubles(5 , 10, 20);
        printStreamContent("Stream of 5 bounded random values:", doubleRandomBoundedStream);

        DoubleStream doubleRandomBoundedInfiniteStream = random.doubles(10, 20);
        printStreamContent("Stream of infinite bounded random values:", doubleRandomBoundedInfiniteStream.limit(5));

        IntStream intRandomStream = ThreadLocalRandom.current().ints(5, 10,20);
        printStreamContent("Stream of 5 bounded random int values:", intRandomStream);

        // Files
        try (Stream<Path> fileStream = Files.list(Paths.get("."))) {
            printStreamContent("Stream files list:", fileStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (Stream<Path> fileStream = Files.walk(Paths.get("."),2)) {
            printStreamContent("Stream files walk:", fileStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (Stream<Path> fileStream = Files.find(Paths.get("."),2, (path, attr) -> path.getFileName().endsWith("gradle"))) {
            printStreamContent("Stream files find gradle:", fileStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (Stream<String> fileStream = Files.lines(Paths.get("./Demo.txt"), StandardCharsets.UTF_8)) {
            printStreamContent("Stream Demo.txt lines:", fileStream.limit(2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (Stream<String> fileStream = Files.newBufferedReader(Paths.get("./Demo.txt"), StandardCharsets.UTF_8).lines()) {
            printStreamContent("Stream lines from buffered reader:", fileStream.limit(2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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
