package dev.Abhishek.PaymentService.service;

public interface RazorpayWebhookService {
    void processWebhook(String payload);
}
