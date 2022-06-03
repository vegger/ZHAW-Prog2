package ch.zhaw.prog2.functional.streaming.finance;


import ch.zhaw.prog2.functional.streaming.Company;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

/**
 * This test class is for all test methods written by students for easier review by lecturers.
 * In a real application these test would be in the class PayrollCreatorTest.
 *
 * ✅  This class should be worked on by students.
 */
public class PayrollCreatorTestStudent {
    @Test
    void payrollAmountByCurrencyTest() {
        Payroll payroll = mock(Payroll.class);
        List<Payment> paymentList = new ArrayList<>();
        Payment payment1 = mock(Payment.class);
        Payment payment2 = mock(Payment.class);
        CurrencyAmount currencyAmount = mock(CurrencyAmount.class);
        when(currencyAmount.getAmount()).thenReturn(30);
        when(currencyAmount.getCurrency()).thenReturn(Currency.getInstance("CHF"));
        when(payment1.getCurrencyAmount()).thenReturn(currencyAmount);
        when(payment2.getCurrencyAmount()).thenReturn(currencyAmount);
        paymentList.add(payment1);
        paymentList.add(payment2);

        when(payroll.getPaymentList()).thenReturn(paymentList);

        PayrollCreator.payrollAmountByCurrency(payroll);

        verify(currencyAmount, times(2)).getCurrency(); // 2 because both payments have the same currencyAmount Mock
    }
}
