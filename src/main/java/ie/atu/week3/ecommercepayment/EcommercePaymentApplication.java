package ie.atu.week3.ecommercepayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EcommercePaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommercePaymentApplication.class, args);
    }

}
