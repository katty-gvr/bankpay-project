package ru.bankpay.bankpay.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bankpay.bankpay.account.Account;
import ru.bankpay.bankpay.account.AccountRepository;
import ru.bankpay.bankpay.user.User;
import ru.bankpay.bankpay.user.UserMapper;
import ru.bankpay.bankpay.user.dto.UserCreateRequest;
import ru.bankpay.bankpay.user.dto.UserCreateResponse;
import ru.bankpay.bankpay.user.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public UserCreateResponse registerNewUser(UserCreateRequest request) {
        User user = UserMapper.mapUserCreateRequestToEntity(request);

        Account account = createAccountForNewUser(request.getInitialAmount());
        user.setAccount(account);
        userRepository.save(user);

        log.info("Создан новый пользователь {},", user);

        return UserCreateResponse.builder()
            .login(user.getLogin())
            .initialAmount(account.getBalance())
            .build();
    }

    private Account createAccountForNewUser(Double amount) {
        Account account = new Account();
        account.setBalance(amount);
        return accountRepository.save(account);
    }
}
