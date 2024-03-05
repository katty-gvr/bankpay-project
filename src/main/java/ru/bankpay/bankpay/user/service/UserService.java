package ru.bankpay.bankpay.user.service;

import ru.bankpay.bankpay.user.dto.UserCreateRequest;
import ru.bankpay.bankpay.user.dto.UserCreateResponse;

public interface UserService {

    UserCreateResponse registerNewUser(UserCreateRequest request);

}
