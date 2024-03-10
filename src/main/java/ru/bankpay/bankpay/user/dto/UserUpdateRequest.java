package ru.bankpay.bankpay.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequest {
    String newPhone;
    @Email
    String newEmail;
    String phoneForDelete;
    @Email
    String emailForDelete;
}
