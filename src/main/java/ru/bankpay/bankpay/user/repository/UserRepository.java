package ru.bankpay.bankpay.user.repository;

import org.springframework.data.domain.Pageable;
import ru.bankpay.bankpay.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByBirthdayAfter(LocalDate birthday, Pageable pageable);

    User findByPhonesContaining(String phoneNumber);

    User findByEmailsContaining(String email);

    List<User> findByFullNameStartsWith(String fullName, Pageable pageable);
}
