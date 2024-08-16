package dev.Abhishek.PaymentService.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayClientConfig {

    private String razorpayClientKey ;
    private String razorpayClientSecret;

    public RazorpayClientConfig(@Value("{razorpay.client.key}")String razorpayClientKey, @Value("{razorpay.client.secret}")String razorpayClientSecret) {
        this.razorpayClientKey = "rzp_test_3W70cQMuXbOy7K";
        this.razorpayClientSecret = "aWiPATd6u6RIK6S4yGT19cxc";
    }

    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayClientKey, razorpayClientSecret);
        return razorpay;
    }

}
