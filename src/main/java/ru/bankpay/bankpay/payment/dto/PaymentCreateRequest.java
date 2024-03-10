package ru.bankpay.bankpay.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCreateRequest {

    @NotNull
    @Positive
    Long accountIdFrom;
    @NotNull
    @Positive
    Long accountIdTo;
    @NotNull
    @Positive
    Double amount;
}
