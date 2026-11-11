package com.financetracker;

import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FinanceService {

    private final List<Transaction> transactions = new ArrayList<>();

    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Transaction addTransaction(Transaction transaction) {

        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction cannot be null."
            );
        }

        if (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        if (transaction.getType() == null
                || transaction.getCategory() == null
                || transaction.getDate() == null) {
            throw new IllegalArgumentException(
                    "Transaction fields cannot be null."
            );
        }

        if (transaction.getDescription() == null
                || transaction.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Description cannot be empty."
            );
        }

        transactions.add(transaction);

        return transaction;
    }

    public Transaction createTransaction(
            TransactionType type,
            BigDecimal amount,
            Category category,
            String description,
            LocalDate date
    ) {
        int id = idGenerator.getAndIncrement();

        Transaction transaction = new Transaction(
                id,
                type,
                amount,
                category,
                description,
                date
        );

        return addTransaction(transaction);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> filterTransactions(
            TransactionType type,
            Category category,
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException(
                    "dateFrom cannot be after dateTo."
            );
        }

        return transactions.stream()
                .filter(transaction ->
                        type == null || transaction.getType() == type
                )
                .filter(transaction ->
                        category == null || transaction.getCategory() == category
                )
                .filter(transaction ->
                        dateFrom == null || !transaction.getDate().isBefore(dateFrom)
                )
                .filter(transaction ->
                        dateTo == null || !transaction.getDate().isAfter(dateTo)
                )
                .collect(Collectors.toList());
    }

    public void loadTransactions(List<Transaction> loadedTransactions) {
        transactions.clear();

        for (Transaction transaction : loadedTransactions) {
            addTransaction(transaction);
        }
    }

    public BigDecimal getTotalIncome() {

        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                total = total.add(transaction.getAmount());
            }
        }

        return total;
    }

    public BigDecimal getTotalExpenses() {

        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.EXPENSE) {
                total = total.add(transaction.getAmount());
            }
        }

        return total;
    }

    public BigDecimal getBalance() {
        return getTotalIncome().subtract(getTotalExpenses());
    }
}
