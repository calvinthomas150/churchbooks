package org.churchbooks.churchbooks.dto;

import java.math.BigDecimal;

public record CategoryDetails(
        String name,
        BigDecimal amount
) {
}
