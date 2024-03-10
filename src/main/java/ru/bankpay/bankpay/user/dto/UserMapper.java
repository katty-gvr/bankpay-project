package ru.bankpay.bankpay.user.dto;

import org.springframework.stereotype.Component;
import ru.bankpay.bankpay.user.entity.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    public static User mapUserCreateRequestToEntity(UserCreateRequest request) {
        return User.builder()
            .fullName(request.getFullName())
            .login(request.getLogin())
            .password(request.getPassword())
            .birthday(request.getBirthday())
            .emails(new HashSet<>(Set.of(request.getEmail())))
            .phones(Set.of(request.getPhoneNumber()))
            .build();
    }

    public static UserSearchResponse mapUserToSearchResponse(User user) {
        return UserSearchResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .birthday(user.getBirthday())
            .build();
    }
}
