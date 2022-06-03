package ch.zhaw.it.prog2.functional.optional;

import java.util.Optional;

public class Shop {
    private final boolean hasLoggedInUser;

    public Shop(boolean hasLoggedInUser) {
        this.hasLoggedInUser = hasLoggedInUser;
    }

    public Optional<User> getLoggedInUser() {
        if (hasLoggedInUser) {
            User user = new User("Muster Andrea", "muster.andrea");
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}
