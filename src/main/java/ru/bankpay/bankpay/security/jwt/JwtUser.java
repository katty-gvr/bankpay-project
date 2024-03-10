package ru.bankpay.bankpay.security.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class JwtUser extends User {
    private final Long accountId;
    private final Long userId;

    public JwtUser(
        String login,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        Long accountId,
        Long userId) {

        super(login, password, authorities);
        this.accountId = accountId;
        this.userId = userId;
    }
}
