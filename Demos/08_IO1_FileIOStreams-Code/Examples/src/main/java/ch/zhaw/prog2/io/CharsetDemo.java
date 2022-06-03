package ch.zhaw.prog2.io;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CharsetDemo {

    public static void main(String[] args) {
        printStandardCharsets();
        printDefaultCharset();
        printAvailableCharsets();
    }

    static void printDefaultCharset() {
        System.out.println("Default Charset:   " + Charset.defaultCharset());
    }

    static void printStandardCharsets () {
        System.out.println("Standard Charsets: " +
            Arrays.stream(StandardCharsets.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(Field::getName)
                .collect(Collectors.joining(", "))
        );
    }

    static void printAvailableCharsets() {
        System.out.println("Available Charsets:");
        for (Charset charset: Charset.availableCharsets().values()) {
            System.out.printf("- Charset: %-20s Aliases: %s%n", charset.name(), String.join(", ", charset.aliases()));
        }
    }

}
