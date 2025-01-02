package ie.atu.week3.ecommercepayment.Consumer;




import ie.atu.week3.ecommercepayment.DTO.Payment;
import ie.atu.week3.ecommercepayment.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "consumer.enabled", havingValue = "true")
public class PaymentConsumer {
    @RabbitListener(queues = "paymentQueue")
    public void processPaymentMessage(Payment payment) {
        System.out.println("Received message: " + payment.toString());
    }
}
