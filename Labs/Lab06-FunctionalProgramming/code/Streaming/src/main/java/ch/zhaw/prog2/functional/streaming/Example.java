package ch.zhaw.prog2.functional.streaming;

import java.util.ArrayList;

public class Example {
    public static void main(String[] args) {
        ArrayList<String> al = new ArrayList<>();
        boolean objectFound = false;
        al.add("hallo");
        al.add("Welt");
        al.forEach(s -> {
            if(s.equals("Welt")) {
                System.out.println("fündig geworden");
                objectFound = true; //TODO check if work
            }
        });

        if (objectFound) {
            System.out.println("FOUND");
        } else {
            System.out.println("NOT FOUND");
        }
    }
}
