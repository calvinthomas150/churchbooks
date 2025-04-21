# Design Decisions

## Transactions
We assume unique fit ids. For duplicate transactions only log and send notification but do persist.
Audit log: <strategy for audit log>

## Pending
Start by persisting transaction to db as is. Setup test containers
Then refine fields
Add Payee entity to schema design. insert photo from draw.io

## Decision Log

### Historical Data
The application should be able to track effectively both transactions inputted directly by the user and through ofx parsing
#### Options
Different endpoint for loading from ofx and for direct input

### File Uploads - handling invalid files
#### Options
- Save invalid files but in different location
- Only save files after validating

### Database interactions - ORM (JPA) vs no ORM (JDBC)
#### Options
- Use JPA to interact with database. Abstracts table creation and supports more sophisticated CRUD queries
- Use JDBC only. Requires DDL script, support for basic CRUD queries. Removes need for Lombok dependency.

### Subscriptions / Repeating transactions
#### Options
- Extra field in request for frequency

### Budget Implementation
- Default budget span is one time.
- Amount allocations can be greater than the total budget.
- Budget allocation is denormalized and thus computed each time a transaction is saved. This improves budget read time but increases write time for transactions

## Unsupported features
- Storing credit card information