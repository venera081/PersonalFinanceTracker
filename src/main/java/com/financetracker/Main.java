package com.financetracker;

import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        FinanceService financeService = new FinanceService();
        FileStorage fileStorage = new FileStorage();
        Scanner scanner = new Scanner(System.in);

        try {
            List<Transaction> savedTransactions = fileStorage.load();
            financeService.loadTransactions(savedTransactions);

            System.out.println(
                    "Loaded transactions: " + savedTransactions.size()
            );

        } catch (IOException | IllegalArgumentException exception) {
            System.out.println(
                    "Could not load transactions: " + exception.getMessage()
            );
            scanner.close();
            return;
        }

        int nextId = financeService.getTransactions()
                .stream()
                .mapToInt(Transaction::getId)
                .max()
                .orElse(0) + 1;

        boolean running = true;

        while (running) {

            System.out.println("\n===== PERSONAL FINANCE TRACKER =====");
            System.out.println("1. Add income");
            System.out.println("2. Add expense");
            System.out.println("3. View transaction history");
            System.out.println("4. View financial summary");
            System.out.println("5. Save transactions");
            System.out.println("0. Save and exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {

                    case "1":
                        nextId = addTransaction(
                                scanner, financeService,
                                TransactionType.INCOME, nextId
                        );
                        break;

                    case "2":
                        nextId = addTransaction(
                                scanner, financeService,
                                TransactionType.EXPENSE, nextId
                        );
                        break;

                    case "3":
                        System.out.println("\n===== TRANSACTION HISTORY =====");

                        List<Transaction> transactions =
                                financeService.getTransactions();

                        if (transactions.isEmpty()) {
                            System.out.println("No transactions yet.");
                        } else {
                            for (Transaction transaction : transactions) {
                                System.out.println(transaction);
                            }
                        }
                        break;

                    case "4":
                        System.out.println("\n===== FINANCIAL SUMMARY =====");
                        System.out.println(
                                "Total income: " +
                                        financeService.getTotalIncome()
                        );
                        System.out.println(
                                "Total expenses: " +
                                        financeService.getTotalExpenses()
                        );
                        System.out.println(
                                "Balance: " + financeService.getBalance()
                        );
                        break;

                    case "5":
                        fileStorage.save(financeService.getTransactions());
                        System.out.println("Transactions saved.");
                        break;

                    case "0":
                        fileStorage.save(financeService.getTransactions());
                        System.out.println("Transactions saved. Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } catch (IOException exception) {
                System.out.println(
                        "File operation failed: " + exception.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static int addTransaction(
            Scanner scanner,
            FinanceService financeService,
            TransactionType type,
            int nextId
    ) {

        BigDecimal amount;

        while (true) {
            System.out.print("Enter amount: ");

            try {
                amount = new BigDecimal(scanner.nextLine().trim());

                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    continue;
                }

                break;

            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number, e.g. 450.50.");
            }
        }

        Category[] categories = Category.values();

        while (true) {
            System.out.println("Choose category:");

            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }

            System.out.print("Enter category number: ");

            try {
                int categoryNumber =
                        Integer.parseInt(scanner.nextLine().trim());

                if (categoryNumber >= 1
                        && categoryNumber <= categories.length) {

                    Category category = categories[categoryNumber - 1];

                    System.out.print("Enter description: ");
                    String description = scanner.nextLine().trim();

                    while (description.isEmpty()) {
                        System.out.print(
                                "Description cannot be empty. Try again: "
                        );
                        description = scanner.nextLine().trim();
                    }

                    Transaction transaction = new Transaction(
                            nextId,
                            type,
                            amount,
                            category,
                            description,
                            LocalDate.now()
                    );

                    financeService.addTransaction(transaction);

                    System.out.println("Transaction added successfully!");

                    return nextId + 1;

                } else {
                    System.out.println("Invalid category number.");
                }

            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid category number.");
            }
        }
    }
}
