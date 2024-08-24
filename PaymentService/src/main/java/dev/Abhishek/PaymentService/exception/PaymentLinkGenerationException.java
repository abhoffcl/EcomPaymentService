package dev.Abhishek.PaymentService.exception;

public class PaymentLinkGenerationException extends RuntimeException{
    public PaymentLinkGenerationException(String message) {
        super(message);
    }
}
