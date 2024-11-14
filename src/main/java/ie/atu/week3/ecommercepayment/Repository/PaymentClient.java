package ie.atu.week3.ecommercepayment.Repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "customers", url = "http://localhost:8081/customers")
public interface PaymentClient {
    @GetMapping("/confirm-paymentService")
    public String confirmPaymentService();
}
