package com.financetracker;

import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinanceService {

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {

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
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
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
