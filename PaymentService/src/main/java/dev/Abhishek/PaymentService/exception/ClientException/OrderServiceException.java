package dev.Abhishek.PaymentService.exception.ClientException;

public class OrderServiceException extends RuntimeException{
    public OrderServiceException(String message) {
        super(message);
    }
}
