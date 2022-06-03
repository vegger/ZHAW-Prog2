package ch.zhaw.prog2.functional.streaming.finance;

import ch.zhaw.prog2.functional.streaming.Company;
import ch.zhaw.prog2.functional.streaming.humanresource.Employee;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This Class creates a Payroll (Lohabrechnung) for a whole Company
 * and supplies some Utility Methods for a a Payroll.
 * ✅  This class should be worked on by students.
 */
public class PayrollCreator {
    private final Company company;

    /**
     * Opens a Payroll for a company.
     *
     * @param company
     */
    public PayrollCreator(Company company) {
        this.company = company;
    }

    /*
     * Aufgabe e) - Test dazu existiert in PayrollCreatorTest
     */
    public static int payrollValueCHF(Payroll payroll) {
        return payroll.getPaymentList().stream()
            .map(p -> CurrencyChange.getInNewCurrency(p.getCurrencyAmount(), Currency.getInstance("CHF")))
            .map(CurrencyAmount::getAmount)
            .reduce(0, Integer::sum);
    }

    /**
     * Aufgabe f) - schreiben Sie einen eigenen Test in PayrollCreatorTestStudent
     *
     * @return a List of total amounts in this currency for each currency in the payroll
     */
    public static List<CurrencyAmount> payrollAmountByCurrency(Payroll payroll) {
        List<CurrencyAmount> result = new ArrayList<>();
        Map<Currency, List<Payment>> groupedPayments = payroll.getPaymentList().stream().collect(Collectors.groupingBy(p -> p.getCurrencyAmount().getCurrency()));

        groupedPayments.forEach((key, value) -> {
            int amount = value.stream()
                .map(payment -> payment.getCurrencyAmount().getAmount())
                .reduce(Integer::sum).get();

            CurrencyAmount currencyAmount = new CurrencyAmount(amount);
            result.add(currencyAmount);
        });

        return result;
    }

    /*
     * Aufgabe d) - Test dazu exisitert in PayrollCreatorTest
     */
    public Payroll getPayrollForAll() {
        Payroll payroll = new Payroll();
        List<Payment> payments;
        payments = company.getAllEmployees().stream().filter(Employee::isWorkingForCompany).map(e -> {
            Payment payment = new Payment();
            payment.setBeneficiary(e);
            payment.setCurrencyAmount(e.getYearlySalary());
            return payment;
        }).collect(Collectors.toList());
        payroll.addPayments(payments);

        return payroll;
    }


}
