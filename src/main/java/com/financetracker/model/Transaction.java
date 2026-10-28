package com.financetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private final int id;
    private final TransactionType type;
    private final BigDecimal amount;
    private final Category category;
    private final String description;
    private final LocalDate date;

    public Transaction(
            int id,
            TransactionType type,
            BigDecimal amount,
            Category category,
            String description,
            LocalDate date
    ) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}