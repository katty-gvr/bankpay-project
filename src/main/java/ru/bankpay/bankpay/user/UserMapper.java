package ru.bankpay.bankpay.user;

import org.springframework.stereotype.Component;
import ru.bankpay.bankpay.user.User;
import ru.bankpay.bankpay.user.dto.UserCreateRequest;

import java.util.Set;

@Component
public class UserMapper {

    public static User mapUserCreateRequestToEntity(UserCreateRequest request) {
        return User.builder()
            .fullName(request.getFullName())
            .login(request.getLogin())
            .password(request.getPassword())
            .birthday(request.getBirthday())
            .emails(Set.of(request.getEmail()))
            .phones(Set.of(request.getPhoneNumber()))
            .build();
    }


}
