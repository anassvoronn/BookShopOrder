package org.nastya.repository;

import org.nastya.entity.TransactionsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsHistoryRepository extends JpaRepository<TransactionsHistory, Integer> {

    @Query("""
             SELECT t.balance FROM TransactionsHistory t
             WHERE t.userId = :userId ORDER BY t.date DESC LIMIT 1
            """)
    Optional<Double> findCurrentBalanceByUserId(@Param("userId") Integer userId);

    List<TransactionsHistory> findByUserIdOrderByDateDesc(Integer userId);
}