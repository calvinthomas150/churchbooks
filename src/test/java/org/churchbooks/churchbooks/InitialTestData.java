package org.churchbooks.churchbooks;

import java.util.UUID;

public class InitialTestData {
    /** Primary key of the Category Entity available at database initialisation */
    public static UUID defaultCategoryId = UUID.fromString("6f1b7575-cb5e-4114-9ca1-32356a0e78f7");
    /** Primary key of the Budget Entity available at database initialisation */
    public static UUID defaultBudgetId = UUID.fromString("24bf4a61-f801-4de8-aa68-651ca0e1986b");
    public static UUID defaultTransactionId = UUID.fromString("0b27873a-58a2-40f2-9136-166c6ebd4f82");
}
