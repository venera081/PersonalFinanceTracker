package com.financetracker.controller;

import com.financetracker.FinanceService;
import com.financetracker.dto.TransactionRequest;
import com.financetracker.model.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return financeService.getTransactions();
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