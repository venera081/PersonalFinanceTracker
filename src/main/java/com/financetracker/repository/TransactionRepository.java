package com.financetracker.repository;

import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t
            WHERE (:type IS NULL OR t.type = :type)
            AND (:category IS NULL OR t.category = :category)
            AND (:dateFrom IS NULL OR t.date >= :dateFrom)
            AND (:dateTo IS NULL OR t.date <= :dateTo)
            """)
    List<Transaction> findFiltered(
            @Param("type") TransactionType type,
            @Param("category") com.financetracker.model.Category category,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo
    );

    List<Transaction> findByType(TransactionType type);
}