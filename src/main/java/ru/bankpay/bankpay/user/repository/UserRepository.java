package ru.bankpay.bankpay.user.repository;

import ru.bankpay.bankpay.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
