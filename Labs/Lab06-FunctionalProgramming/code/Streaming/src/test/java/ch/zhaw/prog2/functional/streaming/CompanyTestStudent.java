package ch.zhaw.prog2.functional.streaming;

import ch.zhaw.prog2.functional.streaming.humanresource.Employee;
import ch.zhaw.prog2.functional.streaming.humanresource.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class is for all test methods written by students for easier review by lecturers.
 * In a real application these test would be in the class CompanyTest.
 *
 * ✅  This class should be worked on by students.
 */
public class CompanyTestStudent {
    private Company testCompany;

    @BeforeEach
    void setUp() {
        Random random = new Random(CompanyTest.RANDOM_SEED);
        CompanySupplier companySupplier = new CompanySupplier(random, CompanyTest.EMPLOYEE_COUNT);
        testCompany = companySupplier.get();
    }

    /*
     * Aufgabe c)
     */
    @Test
    void getEmployeesByPredicate() {
        List<Employee> allEmployees = testCompany.getAllEmployees();
        List<Employee> femaleEmployees = testCompany.getEmployeesByPredicate(Person::isFemale);
        List<Employee> maleEmployees = testCompany.getEmployeesByPredicate(Predicate.not(Person::isFemale));
        assert(allEmployees.size() == femaleEmployees.size()+maleEmployees.size());
    }

}
