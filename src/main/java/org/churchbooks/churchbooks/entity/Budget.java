package org.churchbooks.churchbooks.entity;

import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.enums.Frequency;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record Budget(
        @Id UUID id,
        String name,
        BigDecimal total,
        Frequency frequency,
        BigDecimal allocated,
        Timestamp createdAt
        ) {

        public Budget(String name, BigDecimal totalAmount, Frequency frequency){
                this(null, name, totalAmount, frequency, BigDecimal.ZERO, Timestamp.from(Instant.now()));
        }

        public Budget update(BudgetDetails budgetDetails){
                return new Budget(
                        id,
                        budgetDetails.name(),
                        budgetDetails.amount(),
                        budgetDetails.frequency(),
                        allocated,
                        createdAt
                );
        }

        public Budget updateAllocated(BigDecimal newAllocation){
                return new Budget(id, name, total, frequency, newAllocation, createdAt);
        }
}