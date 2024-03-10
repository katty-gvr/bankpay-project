package ru.bankpay.bankpay.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bankpay.bankpay.payment.dto.PaymentCreateRequest;
import ru.bankpay.bankpay.payment.dto.PaymentCreateResponse;
import ru.bankpay.bankpay.payment.service.PaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentCreateResponse makePayment(@RequestBody PaymentCreateRequest request) {
        return paymentService.makePayment(request);
    }
}
