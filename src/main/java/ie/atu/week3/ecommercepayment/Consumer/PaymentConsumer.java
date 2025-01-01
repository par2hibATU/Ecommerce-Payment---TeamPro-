package ie.atu.week3.ecommercepayment.Consumer;




import ie.atu.week3.ecommercepayment.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {
    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void listen(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
    }
}
