package org.churchbooks.churchbooks.enums;

public enum TransactionType {
    /**
     * Move money into a tracked account.
     */
    CREDIT,
    /**
     * Move money from a tracked account.
     */
    DEBIT,
    /**
     * Transfer from one tracked account to another
     */
    TRANSFER
    }
