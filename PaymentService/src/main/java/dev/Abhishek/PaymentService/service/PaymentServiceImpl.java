package dev.Abhishek.PaymentService.service;

import com.razorpay.PaymentLink;
import dev.Abhishek.PaymentService.dto.PaymentRequestDto;
import dev.Abhishek.PaymentService.config.RazorpayClientConfig;
import dev.Abhishek.PaymentService.entity.Currency;
import dev.Abhishek.PaymentService.entity.Payment;
import dev.Abhishek.PaymentService.entity.PaymentStatus;
import dev.Abhishek.PaymentService.exception.PaymentNotFoundException;
import dev.Abhishek.PaymentService.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;
    private RazorpayClientConfig razorpayClientConfig;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RazorpayClientConfig razorpayClientConfig) {
        this.paymentRepository = paymentRepository;
        this.razorpayClientConfig = razorpayClientConfig;
    }

    @Override
    public String generatePaymentLink(PaymentRequestDto requestDto) throws RazorpayException {
        // TODO: 02-06-2024  :  insert payment request dto in payment service
        //  db->payment table with status INPROGRESS
        Payment savedPayment = createPayment(requestDto);

        RazorpayClient razorpay = razorpayClientConfig.getRazorpayClient();
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", requestDto.getAmount());
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", false);
        paymentLinkRequest.put("first_min_partial_amount", 100);
        paymentLinkRequest.put("expire_by", Instant.now().toEpochMilli() + 600000);
        paymentLinkRequest.put("reference_id", requestDto.getOrderId());
        paymentLinkRequest.put("description", "Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name", requestDto.getCustomerName());
        customer.put("contact", requestDto.getCustomerPhone());
        customer.put("email", requestDto.getCustomerEmail());
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
//        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);
//        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
//        paymentLinkRequest.put("callback_method","get");

        PaymentLink payLink = razorpay.paymentLink.create(paymentLinkRequest);
        return payLink.toString();
    }

    public Payment createPayment(PaymentRequestDto requestDto) {
        Payment payment = new Payment();
        payment.setAmount(requestDto.getAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setUserId(UUID.fromString(requestDto.getUserId()));
        payment.setOrderId(UUID.fromString(requestDto.getOrderId()));
        Currency currency = new Currency();
        currency.setCurrencyName("INDIAN RUPEES");
        currency.setCurrencyTag("INR");
        currency.setCountry("INDIA");
        payment.setCurrency(currency);
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;
    }

    public void updatePaymentStatus(UUID orderId, PaymentStatus status) {
        Payment savedPayment = paymentRepository.findByOrderId(orderId);
        if (savedPayment != null) {
            savedPayment.setStatus(status);
            Payment updatedPayment = paymentRepository.save(savedPayment);
        } else {
            throw new PaymentNotFoundException("payment not found for order id " + orderId);
        }
    }
}
