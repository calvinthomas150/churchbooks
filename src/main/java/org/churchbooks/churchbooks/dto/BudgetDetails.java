package org.churchbooks.churchbooks.dto;

import org.churchbooks.churchbooks.enums.Frequency;

import java.math.BigDecimal;

public record BudgetDetails(
        String name,
        BigDecimal amount,
        Frequency frequency
) {
}
