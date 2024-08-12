package dev.Abhishek.PaymentService.service;

import com.razorpay.RazorpayException;
import dev.Abhishek.PaymentService.dto.PaymentRequestDto;

public interface PaymentService {
    String generatePaymentLink(PaymentRequestDto requestDto) throws RazorpayException;

}
