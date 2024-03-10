package ru.bankpay.bankpay.payment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bankpay.bankpay.account.Account;
import ru.bankpay.bankpay.account.AccountRepository;
import ru.bankpay.bankpay.exception.NotFoundException;
import ru.bankpay.bankpay.exception.PaymentException;
import ru.bankpay.bankpay.payment.dto.PaymentCreateRequest;
import ru.bankpay.bankpay.payment.dto.PaymentCreateResponse;
import ru.bankpay.bankpay.payment.dto.PaymentMapper;
import ru.bankpay.bankpay.payment.entity.Payment;
import ru.bankpay.bankpay.payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public synchronized PaymentCreateResponse makePayment(PaymentCreateRequest request) {

        Account accountFrom = accountRepository.findById(request.getAccountIdFrom())
            .orElseThrow(() -> new NotFoundException(String.format("Аккаунт с id=%d не найден", request.getAccountIdFrom())));

        Account accountTo = accountRepository.findById(request.getAccountIdTo())
            .orElseThrow(() -> new NotFoundException(String.format("Аккаунт id=%d не найден", request.getAccountIdTo())));

        if (accountFrom.getBalance() - request.getAmount() < 0) {
            throw new PaymentException("Баланс аккаунта после перевода не может быть меньше 0");
        }

        Payment payment = PaymentMapper.mapToPaymentFromRequest(request, accountFrom, accountTo);
        paymentRepository.save(payment);

        accountFrom.setBalance(accountFrom.getBalance() - request.getAmount());
        accountRepository.save(accountFrom);

        accountTo.setBalance(accountTo.getBalance() + request.getAmount());
        accountRepository.save(accountTo);

        log.info("С аккаунта с id {} создан платеж на сумму {} на аккаунт с id {}",
            accountFrom.getId(), request.getAmount(), accountTo.getId());

        return PaymentCreateResponse.builder()
            .message("Платеж успешно создан")
            .build();
    }
}
