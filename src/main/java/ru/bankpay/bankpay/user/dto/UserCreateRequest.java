package ru.bankpay.bankpay.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateRequest {

    @NotBlank
    String login;
    @NotBlank
    String password;
    @NotNull
    Double initialAmount;
    @NotBlank
    String phoneNumber;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String fullName;
    @Past
    @NotNull
    LocalDate birthday;
}
