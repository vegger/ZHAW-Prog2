package ch.zhaw.it.prog2.functional.methodreference;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntBiFunction;

/**
 * Demo for static methods, used on slide 21
 */
public class MaxDemo {
    public static final String SEPARATOR = "====================";
    private final List<Integer> intList = Arrays.asList(5, 3, 11, 7);

    public static void main(String[] args) {
        MaxDemo maxDemo = new MaxDemo();
        maxDemo.showByFunction((a, b) -> a > b ? a : b);
        maxDemo.showByFunction((a, b) -> Math.max(a, b));
        maxDemo.showByFunction(Math::max);
        maxDemo.showByFunction(Integer::sum);
    }

    private void showValues(int previous, int value, int selected) {
        System.out.format("Current value %2d, previous value %11d, selected %2d%n", value, previous, selected);
    }

    /**
     * Compare all elements of a list with its neighbour and select one of the element by the given function
     *
     * @param function to select one of two values
     */
    private void showByFunction(ToIntBiFunction<Integer, Integer> function) {
        int previous = Integer.MIN_VALUE;
        for (int value : intList) {
            int selected = function.applyAsInt(previous, value);
            showValues(previous, value, selected);
            previous = value;
        }
        System.out.println(SEPARATOR);
    }

    /**
     * More universal version of showByFunction - but too verbose for lecture
     *
     * @param function to select one of two values
     */
    private void showByFunctionWithoutFirstElement(ToIntBiFunction<Integer, Integer> function) {
        Iterator<Integer> iterator = intList.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        int previous = iterator.next();
        while (iterator.hasNext()) {
            int value = iterator.next();
            int selected = function.applyAsInt(previous, value);
            showValues(previous, value, selected);
            previous = value;
        }
        System.out.println(SEPARATOR);
    }

}
