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

### Subscriptions / Repeating transactions
#### Options
- Extra field in request for frequency


## Unsupported features
- Storing credit card information

Implementation Details
Entities 
add sql to schema file and use records vs @Entity notation on a class