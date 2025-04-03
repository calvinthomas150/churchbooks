package org.churchbooks.churchbooks.dto;

import java.math.BigDecimal;

public record BudgetDetails(
        String name,
        BigDecimal amount
) {
}
