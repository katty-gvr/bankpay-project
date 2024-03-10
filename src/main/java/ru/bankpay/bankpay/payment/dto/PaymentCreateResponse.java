package ru.bankpay.bankpay.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentCreateResponse {
    String message;
}
