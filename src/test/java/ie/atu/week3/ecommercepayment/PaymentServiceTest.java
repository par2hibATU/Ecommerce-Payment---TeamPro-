package ie.atu.week3.ecommercepayment;


import ie.atu.week3.ecommercepayment.DTO.Payment;
import ie.atu.week3.ecommercepayment.Repository.PaymentRepo;
import ie.atu.week3.ecommercepayment.Service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentRepo paymentRepo;

    @Test
    public void testGetAllPayments() {
        Payment pay1 = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        Payment pay2 = new Payment("2", "Card", "Credit", "2012-08-19", "299.00");

        when(paymentRepo.findAll()).thenReturn(Arrays.asList(pay1, pay2));

        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(2, payments.size());
        assertEquals(pay1.getPaymentType(), payments.get(0).getPaymentType());
    }

    @Test
    public void testAddPayment() {
        Payment pay1 = new Payment(null, "Cash", "null", "2002-12-05", "150.00");
        Payment savedPay = new Payment("1", "Cash", "null", "2002-12-05", "150.00");

        when(paymentRepo.save(pay1)).thenReturn(savedPay);
        Payment resutlt = paymentService.addPayment(pay1);
        assertNotNull(resutlt.getPaymentId());
        assertEquals("Cash", resutlt.getPaymentType());

    }

    @Test
    public void testGetPaymentById() {
        Payment mockPay = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        when(paymentRepo.findById("1")).thenReturn(Optional.of(mockPay));

        Payment result = paymentService.getPaymentById("1");
        assertNotNull(result);
        assertEquals("Cash", result.getPaymentType());
    }

    @Test
    public void testUpdatePayment() {
        Payment existingPay = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        Payment updatedPay = new Payment("1", "Card", "Debit", "2002-12-05", "250.00");

        when(paymentRepo.findById("1")).thenReturn(Optional.of(existingPay));
        when(paymentRepo.save(updatedPay)).thenReturn(updatedPay);

        Payment result = paymentService.updatePaymentDetails("1", updatedPay);
        assertNotNull(result);
        assertEquals("Card", result.getPaymentType());
    }

    @Test
    public void testDeletePayment(){
        when(paymentRepo.findById("1")).thenReturn(Optional.empty());
        String result = paymentService.deletePayment("1");
        assertEquals("Payment ID not found", result);
        verify(paymentRepo, times(0)).deleteById("1");
    }
}
