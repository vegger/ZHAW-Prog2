package ch.zhaw.it.prog2.functional.methodreference;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * This class demonstrates creating objects with a parameter within a stream and with method reference.
 */
public class ConstructorDemo {
    public static void main(String[] args) {
        System.out.println("============ Demo object creation");
        new ConstructorDemo().demoObjectCreation();
        System.out.println("============ Demo create instances in stream");
        new ConstructorDemo().createInstancesInStream();
    }

    // referenced in slide set
    private void demoObjectCreation() {
        IntFunction<Person> personCreator = Person::new;
        Person person = personCreator.apply(22);
        System.out.println(person);
    }

    private class Person {
        private final int age;

        Person(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person of age " + age;
        }
    }

    // self study for students after having learned about streams.
    // change verbose lambda expression using the method reference version
    private void createInstancesInStream() {
        IntFunction<Person> personCreator = Person::new;
        List<Integer> ageList = List.of(5, 15, 25);
        List<Person> personList = ageList.stream()
            .map(age -> personCreator.apply(age))               //.map(personCreator::apply)        //  .map(Person::new)
            .toList();
        personList.forEach(person -> System.out.println(person));  // personList.forEach(System.out::println);
    }
}
