package ch.zhaw.prog2.io.serialize;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable
{
	private static final long serialVersionUID = 21L;
	private final String name;
	private final LocalDate birthday;
	private int salary = 0;

	public Employee(String name, int salary, LocalDate birthday) {
		this.name = name;
		this.birthday = birthday;
		this.salary = salary;
	}
	public String getName() { return name; }
	public LocalDate getBirthday() { return birthday; }
	public int getSalary() { return salary; }
	public void setSalary(int salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "Employee {" +
            "name='" + name + '\'' +
            ", birthday=" + birthday +
            ", salary=" + salary +
            '}';
    }
}
