package ie.atu.week3.ecommercepayment.Repository;

import ie.atu.week3.ecommercepayment.DTO.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends MongoRepository<Payment, String> {
    List<Payment> getPaymentByPaymentType(String paymentType);

    List<Payment> getPaymentByPaymentMethod(String paymentMethod);

    List<Payment> getPaymentByPaymentDate(String paymentDate);
}
