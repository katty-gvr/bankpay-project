package ru.bankpay.bankpay.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bankpay.bankpay.user.dto.UserCreateRequest;
import ru.bankpay.bankpay.user.dto.UserCreateResponse;
import ru.bankpay.bankpay.user.service.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserCreateController {

    private final UserService userService;

    @PostMapping("/register")
    public UserCreateResponse registerNewUser(@RequestBody @Valid UserCreateRequest request) {
        log.info("Добавление нового пользователя {}", request);
        return userService.registerNewUser(request);
    }
}
