package dev.Abhishek.PaymentService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequestDto {
    private double amount;
    private String orderId;
    private String userId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
}
