package com.financetracker.controller;

import com.financetracker.FinanceService;
import com.financetracker.dto.TransactionRequest;
import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public List<Transaction> getTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateTo
    ) {
        return financeService.filterTransactions(
                type,
                category,
                dateFrom,
                dateTo
        );
    }

    @GetMapping("/summary")
    public BigDecimal getBalance() {
        return financeService.getBalance();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(
            @Valid @RequestBody TransactionRequest request
    ) {
        return financeService.createTransaction(
                request.getType(),
                request.getAmount(),
                request.getCategory(),
                request.getDescription(),
                request.getDate()
        );
    }
}