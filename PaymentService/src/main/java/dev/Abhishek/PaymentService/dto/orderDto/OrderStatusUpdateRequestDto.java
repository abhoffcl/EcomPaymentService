package dev.Abhishek.PaymentService.dto.orderDto;

import dev.Abhishek.PaymentService.entity.orderEntity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderStatusUpdateRequestDto {
    private String orderId;
    private OrderStatus status;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
}
