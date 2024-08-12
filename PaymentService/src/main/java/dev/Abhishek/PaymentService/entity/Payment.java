package dev.Abhishek.PaymentService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment extends BaseModel{
    private UUID userId;
    private UUID orderId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String transactionId;
    @OneToOne
    private Currency currency;

}
