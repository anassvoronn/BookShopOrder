package org.nastya.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nastya.enums.OperationType;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "transactions_history")
public class TransactionsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", nullable = false, precision = 15)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false, length = 20)
    private OperationType operationType;

    @Column(name = "date", nullable = false, updatable = false)
    private ZonedDateTime date;

    @Column(name = "balance", nullable = false, precision = 15)
    private double balance;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}