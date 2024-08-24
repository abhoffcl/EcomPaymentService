package dev.Abhishek.PaymentService.service;

import dev.Abhishek.PaymentService.exception.ClientException.OrderServiceException;
import dev.Abhishek.PaymentService.exception.PaymentNotFoundException;

public interface RazorpayWebhookService {
    void processWebhook(String payload)throws PaymentNotFoundException, OrderServiceException;
}
