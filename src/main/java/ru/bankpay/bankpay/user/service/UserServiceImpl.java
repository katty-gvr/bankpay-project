package ru.bankpay.bankpay.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bankpay.bankpay.account.Account;
import ru.bankpay.bankpay.account.AccountRepository;
import ru.bankpay.bankpay.exception.NotFoundException;
import ru.bankpay.bankpay.exception.RestrictionException;
import ru.bankpay.bankpay.user.dto.*;
import ru.bankpay.bankpay.user.entity.User;
import ru.bankpay.bankpay.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserCreateResponse registerNewUser(UserCreateRequest request) {
        User user = UserMapper.mapUserCreateRequestToEntity(request);

        Account account = createAccountForNewUser(request.getInitialAmount());
        user.setAccount(account);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        log.info("Создан новый пользователь {},", user);

        return UserCreateResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .initialAmount(account.getBalance())
            .build();
    }

    @Override
    public UserUpdateResponse updateUserData(Long userId, UserUpdateRequest request) {
        User updatedUser = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(String.format("Пользователь id=%d is не найден", userId)));

        if (request.getPhoneForDelete() != null) {
            if (updatedUser.getPhones().size() == 1) {
                throw new RestrictionException("Нельзя удалить последний номер телефона");
            }
            updatedUser.getPhones().remove(request.getPhoneForDelete());
            log.info("Пользователь {} удалил номер телефона {}", userId, request.getPhoneForDelete());
        }

        if (request.getEmailForDelete() != null) {
            if (updatedUser.getPhones().size() == 1) {
                throw new RestrictionException("Нельзя удалить последний e-mail");
            }
            updatedUser.getEmails().remove(request.getPhoneForDelete());
            log.info("Пользователь {} удалил e-mail {}", userId, request.getEmailForDelete());
        }

        if (request.getNewPhone() != null) {
            updatedUser.getPhones().add(request.getNewPhone());
            log.info("Пользователь {} добавил номер телефона {}", userId, request.getNewPhone());
        }

        if (request.getNewEmail() != null) {
            updatedUser.getEmails().add(request.getNewEmail());
            log.info("Пользователь {} добавил e-mail {}", userId, request.getNewEmail());
        }

        userRepository.save(updatedUser);

        return UserUpdateResponse.builder()
            .message("Данные пользователя успешно обновлены")
            .build();
    }

    @Override
    public List<UserSearchResponse> searchUsers(UserSearchRequest request, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from / size, size, Sort.by("id").ascending());

        List<User> users = new ArrayList<>();

        if (request.getBirthday() != null) {
            users.addAll(userRepository.findByBirthdayAfter(request.getBirthday(), page));
        }

        if (request.getEmail() != null) {
            User user = userRepository.findByEmailsContaining(request.getEmail());
            if (user == null) {
                throw new NotFoundException("Пользователь с таким e-mail не найден");
            }
            users.add(user);
        }

        if (request.getPhoneNumber() != null) {
            User user = userRepository.findByPhonesContaining(request.getPhoneNumber());
            if (user == null) {
                throw new NotFoundException("Пользователь с таким номером телефона не найден");
            }
            users.add(user);
        }

        if (request.getFullName() != null) {
            users.addAll(userRepository.findByFullNameStartsWith(request.getFullName(), page));
        }

        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream()
            .map(UserMapper::mapUserToSearchResponse)
            .collect(Collectors.toList());
    }

    private Account createAccountForNewUser(Double amount) {
        Account account = new Account();
        account.setBalance(amount);
        return accountRepository.save(account);
    }
}
