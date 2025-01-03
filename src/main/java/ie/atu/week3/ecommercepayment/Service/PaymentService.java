package ie.atu.week3.ecommercepayment.Service;

import ie.atu.week3.ecommercepayment.DTO.Payment;
import ie.atu.week3.ecommercepayment.Repository.PaymentRepo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepo paymentRepo;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PaymentService(PaymentRepo paymentRepo, RabbitTemplate rabbitTemplate) {
        this.paymentRepo = paymentRepo;
        this.rabbitTemplate = rabbitTemplate;
    }
    public Payment addPayment(Payment payment){
        rabbitTemplate.convertAndSend("paymentQueue", payment);
        System.out.println("Saved payment: " + payment);
        return paymentRepo.save(payment);
    }

    public List<Payment> getAllPayments(){
        return paymentRepo.findAll();
    }

    public Payment getPaymentById(String paymentId){
        return paymentRepo.findById(paymentId).get();
    }

    public List<Payment> getPaymentByType(String paymentType){
        return paymentRepo.getPaymentByPaymentType(paymentType);
    }

    public List<Payment> getPaymentByMethod(String paymentMethod){
        return paymentRepo.getPaymentByPaymentMethod(paymentMethod);
    }
    public List<Payment> getPaymentByDate(String paymentDate){
        return paymentRepo.getPaymentByPaymentDate(paymentDate);
    }

    public Payment updatePaymentDetails(String paymentId, Payment payment){
        Optional<Payment> queryPayment = paymentRepo.findById(paymentId);
        if(!queryPayment.isPresent()){
            throw new RuntimeException("Payment ID not found");
        }
        Payment existingPayment = queryPayment.get();
        existingPayment.setPaymentType(payment.getPaymentType());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setPaymentDate(payment.getPaymentDate());
        existingPayment.setPaymentAmount(payment.getPaymentAmount());
        return paymentRepo.save(existingPayment);
    }

    public String deletePayment(String paymentId) {
        if (paymentRepo.existsById(paymentId)) {
            paymentRepo.deleteById(paymentId);
            return "Payment details deleted for ID: " + paymentId;
        } else {
            return "Payment ID not found";
        }
    }

}
