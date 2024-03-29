package ru.bankpay.bankpay.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {
    Long id;
    String fullName;
    Double initialAmount;
}
