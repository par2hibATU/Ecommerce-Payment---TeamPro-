package ie.atu.week3.ecommercepayment;


import ie.atu.week3.ecommercepayment.DTO.Payment;
import ie.atu.week3.ecommercepayment.Service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testGetAllPayments() throws Exception {
        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPayment() throws Exception {
        Payment mockPayment = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        when(paymentService.addPayment(new Payment(null, "Cash", "null", "2002-12-05", "150.00"))).thenReturn(mockPayment);

        String paymentJson = "{\"paymentType\":\"Cash\", \"paymentMethod\": \"null\", \"paymentDate\": \"2002-12-05\", \"paymentAmount\": \"150.00\"}";
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentType").value("Cash"))
                .andExpect(jsonPath("$.paymentMethod").value("null"))
                .andExpect(jsonPath("$.paymentDate").value("2002-12-05"))
                .andExpect(jsonPath("$.paymentAmount").value("150.00"));
    }

    @Test
    public void testGetPaymentById() throws Exception {
        Payment mockPayment = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        when(paymentService.getPaymentById("1")).thenReturn(mockPayment);
        try {
            mockMvc.perform(get("/payments/id/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.paymentType").value("Cash"))
                    .andExpect(jsonPath("$.paymentMethod").value("null"))
                    .andExpect(jsonPath("$.paymentDate").value("2002-12-05"))
                    .andExpect(jsonPath("$.paymentAmount").value("150.00"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdatePayment() throws Exception {
        Payment newPayment = new Payment("1", "Cash", "null", "2002-12-05", "150.00");
        Payment updatedPayment = new Payment("1", "Card", "Debit", "2002-12-05", "250.00");

        when(paymentService.updatePaymentDetails("1", updatedPayment)).thenReturn(updatedPayment);
        String paymentJson = "{\"paymentId\":\"1\", \"paymentType\":\"Card\", \"paymentMethod\": \"Debit\", \"paymentDate\":\"2002-12-05\", \"paymentAmount\":\"250.00\"}";
        mockMvc.perform(put("/payments/payment/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentType").value("Card"))
                .andExpect(jsonPath("$.paymentMethod").value("Debit"))
                .andExpect(jsonPath("$.paymentDate").value("2002-12-05"))
                .andExpect(jsonPath("$.paymentAmount").value("250.00"));
    }

    @Test
    public void testDeletePayment() throws Exception {
        when(paymentService.deletePayment("1")).thenReturn("not Found");
        mockMvc.perform(delete("/payments/payment/1"))
                .andExpect(status().isNotFound());
    }

}
