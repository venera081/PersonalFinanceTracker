package com.financetracker;

import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import com.financetracker.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinanceServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FinanceService financeService;

    @Test
    void shouldCreateTransaction() {
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = financeService.createTransaction(
                TransactionType.INCOME,
                new BigDecimal("1500.00"),
                Category.SALARY,
                "Monthly salary",
                LocalDate.of(2026, 11, 25)
        );

        assertNotNull(transaction);
        assertEquals(
                new BigDecimal("1500.00"),
                transaction.getAmount()
        );
        assertEquals(TransactionType.INCOME, transaction.getType());
        assertEquals(Category.SALARY, transaction.getCategory());

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> financeService.createTransaction(
                        TransactionType.EXPENSE,
                        new BigDecimal("-100.00"),
                        Category.FOOD,
                        "Invalid expense",
                        LocalDate.of(2026, 11, 25)
                )
        );

        verifyNoInteractions(transactionRepository);
    }
}
