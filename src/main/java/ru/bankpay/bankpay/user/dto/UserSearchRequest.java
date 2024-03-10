package ru.bankpay.bankpay.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSearchRequest {
    LocalDate birthday;
    String phoneNumber;
    @Email
    String email;
    String fullName;
}
