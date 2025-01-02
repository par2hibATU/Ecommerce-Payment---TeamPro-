package ie.atu.week3.ecommercepayment.Controller;

import ie.atu.week3.ecommercepayment.DTO.Payment;
import ie.atu.week3.ecommercepayment.Repository.PaymentClient;
import ie.atu.week3.ecommercepayment.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentClient paymentClient;

    @Autowired
    public PaymentController(PaymentClient paymentClient, PaymentService paymentService) {
        this.paymentClient = paymentClient;
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment addPayment(@RequestBody Payment payment){
        return paymentService.addPayment(payment);
    }

    @GetMapping("/id/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String paymentId){
        Payment qPayment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(qPayment);
    }

    @GetMapping("/type/{paymentType}")
    public ResponseEntity<List<Payment>> getPaymentsByType(@PathVariable String paymentType){
        List<Payment> qPayment = paymentService.getPaymentByType(paymentType);
        return ResponseEntity.ok(qPayment);
    }

    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<Payment>> getPaymentsByMethod(@PathVariable String paymentMethod){
        List<Payment> qPayment = paymentService.getPaymentByMethod(paymentMethod);
        return ResponseEntity.ok(qPayment);
    }

    @GetMapping("/date/{paymentDate}")
    public ResponseEntity<List<Payment>> getPaymentsByDate(@PathVariable String paymentDate){
        List<Payment> qPayment = paymentService.getPaymentByDate(paymentDate);
        return ResponseEntity.ok(qPayment);
    }

    @PutMapping("/payment/id/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable String paymentId, @RequestBody Payment payment){
        try{
            Payment existingPayment = paymentService.updatePaymentDetails(paymentId, payment);
            return ResponseEntity.ok(existingPayment);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/payment/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable String paymentId){
        String result = paymentService.deletePayment(paymentId);
        if(result.equals("not Found")){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    //to check the connection with customer service
    @GetMapping("check-connection")
    public String checkConnection(){
        return paymentClient.confirmPaymentService();
    }
}
