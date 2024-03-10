package ru.bankpay.bankpay.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bankpay.bankpay.user.dto.*;
import ru.bankpay.bankpay.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserCreateResponse registerNewUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.registerNewUser(request);
    }

    @PatchMapping("/{userId}")
    public UserUpdateResponse updateUserData(@PathVariable("userId") Long userId,
                                             @RequestBody @Valid UserUpdateRequest request) {
        return userService.updateUserData(userId, request);
    }

    @GetMapping("/search")
    public List<UserSearchResponse> searchUsers(@RequestBody @Valid UserSearchRequest request,
                                                @RequestParam(value = "from", required = false, defaultValue = "0")
                                                Integer from,
                                                @RequestParam(value = "size", required = false, defaultValue = "10")
                                                Integer size) {

        return userService.searchUsers(request, from, size);
    }
}
