package ru.bankpay.bankpay.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.bankpay.bankpay.account.Account;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public",name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    @Length(min = 8, message = "Длина пароля должна быть минимум 8 символов")
    private String password;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @ElementCollection
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "email", nullable = false)
    private Set<String> emails = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone_number", nullable = false)
    private Set<String> phones = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;



}
