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
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

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

    @Test
    void shouldRejectZeroAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> financeService.createTransaction(
                        TransactionType.EXPENSE,
                        BigDecimal.ZERO,
                        Category.FOOD,
                        "Zero amount",
                        LocalDate.of(2026, 11, 25)
                )
        );

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> financeService.createTransaction(
                        TransactionType.EXPENSE,
                        new BigDecimal("100.00"),
                        Category.FOOD,
                        "   ",
                        LocalDate.of(2026, 11, 25)
                )
        );

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void shouldUpdateExistingTransaction() {
        Transaction transaction = new Transaction(
                TransactionType.INCOME,
                new BigDecimal("1500.00"),
                Category.SALARY,
                "Old salary",
                LocalDate.of(2026, 11, 25)
        );

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(transaction));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction updatedTransaction = financeService.updateTransaction(
                1L,
                TransactionType.INCOME,
                new BigDecimal("2000.00"),
                Category.SALARY,
                "Updated salary",
                LocalDate.of(2026, 11, 27)
        );

        assertEquals(
                new BigDecimal("2000.00"),
                updatedTransaction.getAmount()
        );
        assertEquals("Updated salary", updatedTransaction.getDescription());
        assertEquals(
                LocalDate.of(2026, 11, 27),
                updatedTransaction.getDate()
        );

        verify(transactionRepository).save(transaction);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTransaction() {
        when(transactionRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> financeService.updateTransaction(
                        999L,
                        TransactionType.EXPENSE,
                        new BigDecimal("100.00"),
                        Category.FOOD,
                        "Test expense",
                        LocalDate.of(2026, 11, 27)
                )
        );

        assertEquals(404, exception.getStatusCode().value());

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldDeleteExistingTransaction() {
        Transaction transaction = new Transaction(
                TransactionType.EXPENSE,
                new BigDecimal("500.00"),
                Category.FOOD,
                "Lunch",
                LocalDate.of(2026, 11, 27)
        );

        when(transactionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(transaction));

        financeService.deleteTransaction(1L);

        verify(transactionRepository).delete(transaction);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTransaction() {
        when(transactionRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> financeService.deleteTransaction(999L)
        );

        assertEquals(404, exception.getStatusCode().value());

        verify(transactionRepository, never()).delete(any(Transaction.class));
    }
}
