package dev.Abhishek.PaymentService.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SchedulingDemo {
    @Scheduled(cron="*/5 * * * * *")
    public void printTimeAndDetails(){
        System.out.println("Hello World "+ LocalDateTime.now());
    }
}
