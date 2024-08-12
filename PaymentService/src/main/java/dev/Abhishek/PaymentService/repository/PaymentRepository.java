package dev.Abhishek.PaymentService.repository;

import dev.Abhishek.PaymentService.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
