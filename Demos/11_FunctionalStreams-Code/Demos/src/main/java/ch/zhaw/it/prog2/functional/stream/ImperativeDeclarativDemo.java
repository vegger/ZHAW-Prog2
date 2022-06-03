package ch.zhaw.it.prog2.functional.stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImperativeDeclarativDemo {

    public static void main(String[] args) {
        List<Integer> list = List.of(3, 2, 12, 5, 6, 11, 13);
        System.out.println("Source list: " + list);

        // imperative
        System.out.println("Sum of even values (imperative): " + evenSumImperative(list));

        // functional
        System.out.println("Sum of even values (functional): " + evenSumFunctional(list));
    }

    static int evenSumImperative(List<Integer> list) {
        // state is stored external -> not pure functional
        int evenSum = 0;
        for (Integer i: list) {
            if (i % 2 == 0) {
                evenSum += i;
            }
        }
        return evenSum;
    }

    static int evenSumFunctional(List<Integer> list) {
        // no external state -> pure functional
        return list.stream()
            .filter(i -> i % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();
    }

    /*
     * not used in slides
     */
    static int evenSumInternalIterator(List<Integer> list) {
        // requires referential type because lamdas require final external variables
        // (final int evenSum = 0) does not work. -> not pure functional
        AtomicInteger evenSum = new AtomicInteger(0);
        list.forEach(i -> {
            if (i % 2 == 0) {
                evenSum.addAndGet(i);
            }
        });
        return evenSum.get();
    }

}
