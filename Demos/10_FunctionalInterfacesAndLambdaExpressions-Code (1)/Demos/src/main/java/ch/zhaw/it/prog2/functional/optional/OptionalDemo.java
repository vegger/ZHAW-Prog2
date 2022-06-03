package ch.zhaw.it.prog2.functional.optional;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Demo how java.util.Optinal can be used
 */
public class OptionalDemo {
    public static void main(String[] args) {
        Consumer<Shop> functionToUse = switch (0) {
            case 1 -> OptionalDemo::useConsumer;
            case 2 -> OptionalDemo::useConsumerAndRunnable;
            case 3 -> OptionalDemo::useOtherUser;
            case 4 -> OptionalDemo::createUserUsingSupplierFunction;
            default -> OptionalDemo::showUserOf;
        };
        boolean hasLoggedInUser = true;
        Shop shop = new Shop(hasLoggedInUser);
        functionToUse.accept(shop);

        hasLoggedInUser = false;
        shop = new Shop(hasLoggedInUser);
        functionToUse.accept(shop);
    }

    private static void showUserOf(Shop shop) {
        Optional<User> user = shop.getLoggedInUser();
        if (user.isEmpty()) {
            System.out.println("No user in our shop");
        } else {
            User realUser = user.get();
            System.out.println("We hava a user: " + realUser);
        }
    }

    private static void useConsumer(Shop shop) {
        Optional<User> optionalUser = shop.getLoggedInUser();
        optionalUser.ifPresent(user -> System.out.format("We have a user %s%n", user));
    }

    private static void useConsumerAndRunnable(Shop shop) {
        Optional<User> optionalUser = shop.getLoggedInUser();
        optionalUser.ifPresentOrElse(user -> System.out.format("We have a user %s%n", user),
            () -> System.out.println("No user"));
    }

    private static void useOtherUser(Shop shop) {
        User demoUser = new User("Demo User", "demo");
        Optional<User> optionalUser = shop.getLoggedInUser();
        User user = optionalUser.orElse(demoUser);
        System.out.format("We always have a user %s%n", user);
    }

    private static void createUserUsingSupplierFunction(Shop shop) {
        Optional<User> optionalUser = shop.getLoggedInUser();
        User user = optionalUser.orElseGet(() -> new User("Created User", "new"));
        System.out.format("We always have a user %s%n", user);
    }
}
