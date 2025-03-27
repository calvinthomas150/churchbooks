drop table if exists transactions;

-- transaction
create table transactions (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    transaction_type VARCHAR(30) NOT NULL,
    date_posted TIMESTAMP NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    status VARCHAR(50) NOT NULL,
    memo VARCHAR(200),
    source VARCHAR(30) NOT NULL,
    version NUMERIC
);