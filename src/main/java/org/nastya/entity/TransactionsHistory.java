package org.nastya.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nastya.enums.OperationType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

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
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false, length = 20)
    private OperationType operationType;

    @Column(name = "date", nullable = false, updatable = false)
    private ZonedDateTime date;

    @Column(name = "balance", nullable = false, precision = 15)
    private BigDecimal balance;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Override
    public String toString() {
        return "TransactionsHistory{" +
                "id=" + id +
                ", amount=" + amount +
                ", operationType=" + operationType +
                ", date=" + date +
                ", balance=" + balance +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionsHistory that = (TransactionsHistory) o;
        return Objects.equals(id, that.id)
                && Objects.equals(amount, that.amount)
                && operationType == that.operationType
                && Objects.equals(balance, that.balance)
                && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, operationType, balance, userId);
    }
}