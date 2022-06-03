package ch.zhaw.prog2.io.serialize;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Manager extends Employee implements Serializable
{
	private static final long serialVersionUID = 4711L;
	private Employee assistant = null;

	public Manager(String name, int salary, LocalDate birthday) {
		super(name, salary, birthday);
	}
	public Employee getAssistant() { return assistant; }
	public void setAssistant(Employee assistant) { this.assistant = assistant; }

    @Override
    public String toString() {
        return "Manager {" +
            "name='" + getName() + '\'' +
            ", birthday=" + getBirthday() +
            ", salary=" + getSalary() +
            ", assistant=" + getAssistant() +
            '}';
    }
}
