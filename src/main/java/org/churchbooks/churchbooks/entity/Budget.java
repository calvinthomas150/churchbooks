package org.churchbooks.churchbooks.entity;

import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record Budget(
        @Id UUID id,
        String name,
        Timestamp createdAt,
        BigDecimal total,
        BigDecimal allocated
        ) {

        public Budget(String name, BigDecimal totalAmount){
                this(null, name, Timestamp.from(Instant.now()), totalAmount, BigDecimal.ZERO);
        }

        public Budget updateBudget(BudgetDetails budgetDetails){
                return new Budget(id, budgetDetails.name(), createdAt, budgetDetails.amount(), allocated);
        }

        public Budget updateAllocated(BigDecimal newAllocation){
                return new Budget(id, name, createdAt, total, newAllocation);
        }
}