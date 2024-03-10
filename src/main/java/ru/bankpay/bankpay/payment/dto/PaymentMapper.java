package ru.bankpay.bankpay.payment.dto;

import ru.bankpay.bankpay.account.Account;
import ru.bankpay.bankpay.payment.dto.PaymentCreateRequest;
import ru.bankpay.bankpay.payment.entity.Payment;

public class PaymentMapper {

    public static Payment mapToPaymentFromRequest(PaymentCreateRequest request, Account from, Account to) {
        return Payment.builder()
            .accountFrom(from)
            .accountTo(to)
            .amount(request.amount)
            .build();
    }
}
