package dev.Abhishek.PaymentService.exception;

public class WebhookProcessingException extends RuntimeException{
    public WebhookProcessingException(String message) {
        super(message);
    }
}
