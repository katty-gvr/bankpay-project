package ru.bankpay.bankpay.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    @Scheduled(fixedRate = 60000) // Запуск каждую минуту
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            double balance = account.getBalance();
            double newBalance = balance * 1.05; // Увеличение баланса на 5%
            if (newBalance <= balance * 2.07) { // Не более 207% от начального депозита
                account.setBalance(newBalance);
            }
        }
        accountRepository.saveAll(accounts);
        log.info("Балансы аккаунтов {} увеличены", accounts);
    }
}
