package dev.Abhishek.PaymentService.repository;

import dev.Abhishek.PaymentService.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
}
