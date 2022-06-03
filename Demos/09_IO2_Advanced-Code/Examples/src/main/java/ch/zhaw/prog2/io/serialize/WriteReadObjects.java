package ch.zhaw.prog2.io.serialize;

import java.io.*;
import java.time.LocalDate;


public class WriteReadObjects {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Employee harry = new Employee("Dirty Harry", 50000, LocalDate.of(1967, 3, 11));
		Manager boss = new Manager("Walter Smith", 80000, LocalDate.of(1950, 12, 4));
		boss.setAssistant(harry);
		Manager anyManager;
        // Save (serialize) two objects to the file empolyee.dat
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))) {
            // objects are written in the given order to the file
            out.writeObject(harry);  // write object Dirty Harry
            out.writeObject(boss);	   // write object Walter Smith
            out.writeInt(12);        // write value of primitive type int
        }

        // Load (deserialize) two objects from the file employee.dat
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat"))) {
            // number and order of reading the objects must match, also the type must be compatible (match or super class)
            Employee e1 = (Employee) in.readObject();   // object for Dirty Harry
            System.out.println("Employee: " + e1);
            Employee e2 = (Employee) in.readObject();   // object for Walter Smith
            System.out.println("Boss: " + e2);
            // e2.getClass().getName() would return type Manager
            int count = in.readInt();	              // read primitive type int
            System.out.println("Count: " + count);
        }

    }
}
