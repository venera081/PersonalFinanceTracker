package com.financetracker;

import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import com.financetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinanceService {

    private final TransactionRepository transactionRepository;

    public FinanceService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(
            TransactionType type,
            BigDecimal amount,
            Category category,
            String description,
            LocalDate date
    ) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        if (type == null || category == null || date == null) {
            throw new IllegalArgumentException(
                    "Transaction fields cannot be null."
            );
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Description cannot be empty."
            );
        }

        Transaction transaction = new Transaction(
                type,
                amount,
                category,
                description,
                date
        );

        return transactionRepository.save(transaction);
    }

    public List<Transaction> filterTransactions(
            TransactionType type,
            Category category,
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        if (dateFrom != null && dateTo != null
                && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException(
                    "dateFrom cannot be after dateTo."
            );
        }

        return transactionRepository.findFiltered(
                type,
                category,
                dateFrom,
                dateTo
        );
    }

    public BigDecimal getTotalIncome() {
        return transactionRepository
                .findByType(TransactionType.INCOME)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalExpenses() {
        return transactionRepository
                .findByType(TransactionType.EXPENSE)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getBalance() {
        return getTotalIncome().subtract(getTotalExpenses());
    }
}