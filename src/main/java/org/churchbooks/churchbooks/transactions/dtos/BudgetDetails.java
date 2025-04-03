package org.churchbooks.churchbooks.transactions.dtos;

import java.math.BigDecimal;

public record BudgetDetails(
        String name,
        BigDecimal amount
) {
}
