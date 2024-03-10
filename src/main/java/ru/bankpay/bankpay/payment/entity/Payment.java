package ru.bankpay.bankpay.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bankpay.bankpay.account.Account;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public",name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_acc_id")
    private Account accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_acc_id")
    private Account accountTo;

    private Double amount;
}
