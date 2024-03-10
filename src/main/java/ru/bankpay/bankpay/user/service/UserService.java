package ru.bankpay.bankpay.user.service;

import ru.bankpay.bankpay.user.dto.*;

import java.util.List;

public interface UserService {

    UserCreateResponse registerNewUser(UserCreateRequest request);

    UserUpdateResponse updateUserData(Long userId, UserUpdateRequest request);

    List<UserSearchResponse> searchUsers(UserSearchRequest request, Integer from, Integer size);
}
