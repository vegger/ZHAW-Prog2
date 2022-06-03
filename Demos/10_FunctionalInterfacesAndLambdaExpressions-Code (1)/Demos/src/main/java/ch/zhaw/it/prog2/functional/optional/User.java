package ch.zhaw.it.prog2.functional.optional;

public class User {
    private final String name;
    private final String login;

    public User(String name, String login) {
        if (name == null || login == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        this.name = name;
        this.login = login;
    }

    @Override
    public String toString() {
        return String.format("User '%s' with login '%s'", name, login);
    }

}
