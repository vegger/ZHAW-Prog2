package ch.zhaw.prog2.functional.streaming.finance;

import ch.zhaw.prog2.functional.streaming.humanresource.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test class is for all test methods written by students for easier review by lecturers.
 * In a real application these test would be in the class PayrollCreatorTest.
 * 
 * ✅  This class should be worked on by students. 
 */
class PayrollCreatorTestStudent {
    private static final Currency CURRENCY_GBP = Currency.getInstance("GBP");
    private static final int GBP_AMOUNT = 5_010;
    private static final Currency CURRENCY_USD = Currency.getInstance("USD");
    private static final int USD_AMOUNT = 7_021;

    Payroll payroll;

    @BeforeEach
    void setUp() {
        payroll = new Payroll();
        addMockPaymentToPayroll(payroll, GBP_AMOUNT, CURRENCY_GBP);
        addMockPaymentToPayroll(payroll, USD_AMOUNT, CURRENCY_USD);
    }

    /*
     * Aufgabe f) - Einen solchen Test müssen die Studierenden schreiben um ihre Methode zu testen.
     * 
     */
    @Test
    void payrollAmountByCurrency() {
        List<CurrencyAmount> currencyAmountList = PayrollCreator.payrollAmountByCurrency_oneStep(new Payroll());
        assertEquals(0, currencyAmountList.size(), "empty payroll");

        currencyAmountList = PayrollCreator.payrollAmountByCurrency_oneStep(payroll);
        assertCurrenciesMatchList(List.of("GBP", "USD"), currencyAmountList);
        assertAmountOfCurrencyInList(currencyAmountList, GBP_AMOUNT, CURRENCY_GBP);
        assertAmountOfCurrencyInList(currencyAmountList, USD_AMOUNT, CURRENCY_USD);

        int chfAmount = 87_543;
        Currency chfCurrency = Currency.getInstance("CHF");
        addMockPaymentToPayroll(payroll, chfAmount, chfCurrency);
        addMockPaymentToPayroll(payroll, USD_AMOUNT, CURRENCY_USD);
        currencyAmountList = PayrollCreator.payrollAmountByCurrency_oneStep(payroll);
        assertCurrenciesMatchList(List.of("CHF", "USD", "GBP"), currencyAmountList);
        assertAmountOfCurrencyInList(currencyAmountList, GBP_AMOUNT, CURRENCY_GBP);
        assertAmountOfCurrencyInList(currencyAmountList, 2 * USD_AMOUNT, CURRENCY_USD);
        assertAmountOfCurrencyInList(currencyAmountList, chfAmount, chfCurrency);
    }

    private void addMockPaymentToPayroll(Payroll payroll, int amount, Currency currency) {
        Payment payment = mock(Payment.class);
        when(payment.getCurrencyAmount()).thenReturn(new CurrencyAmount(amount, currency));
        when(payment.getBeneficiary()).thenReturn(new Person("FirstNameOfPerson", "LastNameOfPerson"));
        payroll.addPayments(List.of(payment));
    }

    private void assertCurrenciesMatchList(List<String> expectedCurrencyCodes, List<CurrencyAmount> currencyAmountsToTest) {
        List<String> foundCurrencies = currencyAmountsToTest.stream()
            .map(CurrencyAmount::getCurrency)
            .map(Currency::getCurrencyCode)
            .sorted()
            .collect(Collectors.toList());
        assertIterableEquals(expectedCurrencyCodes.stream().sorted().collect(Collectors.toList()), foundCurrencies);
    }

    private void assertAmountOfCurrencyInList(List<CurrencyAmount> currencyAmounts, int amount, Currency currency) {
        Optional<CurrencyAmount> foundCurrencyAmount = currencyAmounts.stream()
            .filter(currencyAmount -> currency.getCurrencyCode().equals(currencyAmount.getCurrency().getCurrencyCode()))
            .findFirst();
        assertTrue(foundCurrencyAmount.isPresent());
        assertEquals(amount, foundCurrencyAmount.get().getAmount(), "unexpected amount");
    }
}
