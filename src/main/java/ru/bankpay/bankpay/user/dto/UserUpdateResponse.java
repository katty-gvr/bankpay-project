package ru.bankpay.bankpay.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateResponse {
    String message;
}
