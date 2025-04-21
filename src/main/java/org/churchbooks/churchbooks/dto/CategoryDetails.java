package org.churchbooks.churchbooks.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoryDetails(
        String name,
        BigDecimal amount,
        UUID budgetId
) {
}
