package ie.atu.week3.ecommercepayment.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Payments")
public class Payment {
    @Id
    private String paymentId;
    private String paymentType;        //Card or Cash
    private String paymentMethod;      //Credit-Debit
    private String paymentDate;
    private String paymentAmount;
}
