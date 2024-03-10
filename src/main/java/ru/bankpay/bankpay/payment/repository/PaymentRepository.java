package ru.bankpay.bankpay.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bankpay.bankpay.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
