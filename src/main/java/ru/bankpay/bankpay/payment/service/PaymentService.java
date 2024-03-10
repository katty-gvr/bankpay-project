package ru.bankpay.bankpay.payment.service;

import ru.bankpay.bankpay.payment.dto.PaymentCreateRequest;
import ru.bankpay.bankpay.payment.dto.PaymentCreateResponse;

public interface PaymentService {

    PaymentCreateResponse makePayment(PaymentCreateRequest request);
}
