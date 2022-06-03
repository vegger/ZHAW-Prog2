package ch.zhaw.it.prog2.functional.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Show list handling with Optional - not used in lecture
 */
public class ListDemo {
    private Random random = new Random();

    public static void main(String[] args) {
        ListDemo demo = new ListDemo();
        //demo.random.setSeed(12);
        demo.processList(demo.getUserList());
    }

    private void processList(List<Optional<User>> userList) {
        userList.forEach(optionalUser -> optionalUser.ifPresent(System.out::println));
    }

    private List<Optional<User>> getUserList() {
        List<Optional<User>> userList = new ArrayList<>();
        int counter = 1;
        while (counter < 5) {
            if (random.nextInt(10) > 4) {
                userList.add(Optional.empty());
            } else {
                User user = new User("user " + counter, "login " + counter);
                userList.add(Optional.of(user));
                counter++;
            }
        }
        System.out.format("List with %d Optional<User> generated%n", userList.size());
        return userList;
    }
}
