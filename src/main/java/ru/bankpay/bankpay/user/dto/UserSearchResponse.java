package ru.bankpay.bankpay.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserSearchResponse {
    Long id;
    String fullName;
    LocalDate birthday;
}
