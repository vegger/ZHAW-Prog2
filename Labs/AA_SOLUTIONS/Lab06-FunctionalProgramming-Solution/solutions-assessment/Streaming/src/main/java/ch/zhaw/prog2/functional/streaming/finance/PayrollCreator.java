package ch.zhaw.prog2.functional.streaming.finance;

import ch.zhaw.prog2.functional.streaming.Company;
import ch.zhaw.prog2.functional.streaming.humanresource.Employee;

import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.reducing;

/**
 * This Class creates a Payroll (Lohabrechnung) for a whole Company 
 * and supplies some Utility Methods for a a Payroll. 
 * ✅  This class should be worked on by students. 
 */
public class PayrollCreator {
    private final Company company;

    /**
     * Opens a Payroll for a company. 
     * @param company
     */
    public PayrollCreator(Company company) {
        this.company = company;
    }

    /*
     * Aufgabe d) - Test dazu exisitert in PayrollCreatorTest
     */
    public Payroll getPayrollForAll() {
        Payroll payroll = new Payroll();
        payroll.addPayments(company.getPayments(Employee::isWorkingForCompany));
        return payroll;
    }

    /*
     * Aufgabe e) - Test dazu existiert in PayrollCreatorTest
     */
    public static int payrollValueCHF(Payroll payroll) {
        Currency chf = Currency.getInstance("CHF");
        return payroll.stream().map(payment ->
            CurrencyChange.getInNewCurrency(payment.getCurrencyAmount(), chf))
            .mapToInt(CurrencyAmount::getAmount)
            .sum();
    }

    /**  
     * Aufgabe f) - schreiben Sie einen eigenen Test in PayrollCreatorTestStudent
     * Lösung 1: In zwei Schritten zur Lösung, passt zur Webseite, die in der Aufgabenstellung als Hilfe gegeben wurde.
     * @return a List of total amounts in this currency for each currency in the payroll 
     */
    public static List<CurrencyAmount> payrollAmountByCurrency_twoSteps(Payroll payroll) {
        Stream<Payment> paymentStream = payroll.stream();
        Map<Currency, Integer> currencyMap = paymentStream.
            map(Payment::getCurrencyAmount).
            collect(Collectors.groupingBy(
                CurrencyAmount::getCurrency,
                reducing(
                    0,
                    CurrencyAmount::getAmount,
                    Integer::sum))
            );
        return currencyMap.keySet().stream()
            .map(currency -> new CurrencyAmount(currencyMap.get(currency), currency))
            .collect(Collectors.toList());
    }

    /** 
     * Aufgabe f) - schreiben Sie einen eigenen Test in PayrollCreatorTestStudent
     * Lösung 2: Verarbeitung in einem Stream ohne weitere Deklarationen.
     * @return a List of total amounts in this currency for each currency in the payroll 
     */
    public static List<CurrencyAmount> payrollAmountByCurrency_oneStep(Payroll payroll) {
        return payroll.stream()
            .map(Payment::getCurrencyAmount)
            .collect(Collectors.groupingBy(
                CurrencyAmount::getCurrency,
                reducing((a, b) ->
                    new CurrencyAmount(a.getAmount() + b.getAmount(), a.getCurrency()))
            ))
            .values().stream().map(Optional::get).collect(Collectors.toList());
    }


}
