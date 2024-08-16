package dev.Abhishek.PaymentService.controller;

import com.razorpay.Utils;
import dev.Abhishek.PaymentService.service.RazorpayWebhookServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RazorpayWebhookController {

    private  String razorpayClientSecret ;
    private RazorpayWebhookServiceImpl webhookService;

    public RazorpayWebhookController(@Value("{razorpay.client.secret}") String razorpayClientSecret, RazorpayWebhookServiceImpl webhookService) {
        this.razorpayClientSecret = razorpayClientSecret;
        this.webhookService = webhookService;
    }

    @PostMapping("/rzp/webhook")
    public ResponseEntity<String> handleRazorpayWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature) {

        try {
            // Verify the webhook signature
            Utils.verifyWebhookSignature(payload, signature, "aWiPATd6u6RIK6S4yGT19cxc");

            // Delegate processing to the service
            webhookService.processWebhook(payload);

            return ResponseEntity.ok("Webhook processed successfully");

        } catch (Exception e) {
            // Handle exceptions and return an error response if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing webhook: " + e.getMessage());
        }
    }
}

