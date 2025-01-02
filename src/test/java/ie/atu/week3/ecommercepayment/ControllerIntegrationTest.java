package ie.atu.week3.ecommercepayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.atu.week3.ecommercepayment.DTO.Payment;
import ie.atu.week3.ecommercepayment.Repository.PaymentRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        paymentRepo.deleteAll();
    }

    @Test
    void testAddPayment() throws Exception {
        Payment Payment = new Payment("1", "Cash", "null", "2002-12-05", "150.00");

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Payment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentId").value("1"))
                .andExpect(jsonPath("$.paymentType").value("Cash"))
                .andExpect(jsonPath("$.paymentMethod").value("null"))
                .andExpect(jsonPath("$.paymentDate").value("2002-12-05"))
                .andExpect(jsonPath("$.paymentAmount").value("150.00"));

        Optional<Payment> savedPayment = paymentRepo.findById("1");
        assertThat(savedPayment).isPresent();
        assertThat(savedPayment.get().getPaymentType()).isEqualTo("Cash");
    }

    @Test
    public void testGetAllPayments() throws Exception {
        Payment pay1 = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        Payment pay2 = new Payment("2", "Card", "Credit", "2012-08-19", "299.00");

        paymentRepo.save(pay1);
        paymentRepo.save(pay2);

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentId").value("1"))
                .andExpect(jsonPath("$[1].paymentId").value("2"));

    }

    @Test
    public void testDeletePaymet() throws Exception {
        Payment payment = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        Payment savedPayment = paymentRepo.save(payment);

        mockMvc.perform(delete("/payments/payment/" + savedPayment.getPaymentId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment details deleted for ID: " + savedPayment.getPaymentId()));

        Optional<Payment> deletedPayment = paymentRepo.findById(savedPayment.getPaymentId());
        assertThat(deletedPayment).isNotPresent();
    }

    @Test
    public void testUpdatePayment() throws Exception {
        Payment payment = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        Payment savedPayment = paymentRepo.save(payment);

        Payment updatedPayment = new Payment(savedPayment.getPaymentId(), "Card", "Debit", "2002-12-05", "250.00");
        mockMvc.perform(put("/payments/payment/id/" + savedPayment.getPaymentId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPayment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentType").value("Card"))
                .andExpect(jsonPath("$.paymentMethod").value("Debit"))
                .andExpect(jsonPath("$.paymentDate").value("2002-12-05"))
                .andExpect(jsonPath("$.paymentAmount").value("250.00"));

        Payment updatedPayDb = paymentRepo.findById(savedPayment.getPaymentId()).orElseThrow();
        assertThat(updatedPayDb.getPaymentType()).isEqualTo("Card");
        assertThat(updatedPayDb.getPaymentMethod()).isEqualTo("Debit");
    }
}
