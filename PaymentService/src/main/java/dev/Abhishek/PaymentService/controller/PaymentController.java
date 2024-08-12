package dev.Abhishek.PaymentService.controller;

import com.razorpay.RazorpayException;
import dev.Abhishek.PaymentService.dto.PaymentRequestDto;
import dev.Abhishek.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/initiate")
    ResponseEntity<String>initiatePayment(@RequestBody PaymentRequestDto requestDto) throws RazorpayException {
        return ResponseEntity.ok(paymentService.generatePaymentLink(requestDto));

    }
    @GetMapping("/hello")
    ResponseEntity<String>hello(){
        return ResponseEntity.ok("Hello world from Abhishek's computer");
    }

}
