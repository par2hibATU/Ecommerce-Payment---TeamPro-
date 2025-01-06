package ie.atu.week3.ecommercepayment.Consumer;




import ie.atu.week3.ecommercepayment.DTO.Customer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import static ie.atu.week3.ecommercepayment.RabbitMQConfig.QUEUE;

@Service
@ConditionalOnProperty(name = "consumer.enabled", havingValue = "true")
public class PaymentConsumer {
    @RabbitListener(queues = QUEUE)
    public void processPaymentMessage(Customer customer) {
        System.out.println("Received message: " + customer.toString());
    }
}
