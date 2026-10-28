package com.financetracker;

import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private final Path filePath = Path.of("transactions.csv");

    public void save(List<Transaction> transactions) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

            for (Transaction transaction : transactions) {
                writer.write(
                        transaction.getId() + ";" +
                                transaction.getType() + ";" +
                                transaction.getAmount().toPlainString() + ";" +
                                transaction.getCategory() + ";" +
                                escape(transaction.getDescription()) + ";" +
                                transaction.getDate()
                );

                writer.newLine();
            }
        }
    }

    public List<Transaction> load() throws IOException {

        List<Transaction> transactions = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return transactions;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";", 6);

                if (parts.length != 6) {
                    throw new IOException("Invalid transaction data: " + line);
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    TransactionType type = TransactionType.valueOf(parts[1]);
                    BigDecimal amount = new BigDecimal(parts[2]);
                    Category category = Category.valueOf(parts[3]);
                    String description = unescape(parts[4]);
                    LocalDate date = LocalDate.parse(parts[5]);

                    transactions.add(new Transaction(
                            id, type, amount, category, description, date
                    ));

                } catch (RuntimeException exception) {
                    throw new IOException(
                            "Cannot read transaction: " + line,
                            exception
                    );
                }
            }
        }

        return transactions;
    }

    private String escape(String text) {
        return text.replace("\\", "\\\\")
                .replace(";", "\\;");
    }

    private String unescape(String text) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (char character : text.toCharArray()) {

            if (escaped) {
                result.append(character);
                escaped = false;
            } else if (character == '\\') {
                escaped = true;
            } else {
                result.append(character);
            }
        }

        if (escaped) {
            result.append('\\');
        }

        return result.toString();
    }
}
