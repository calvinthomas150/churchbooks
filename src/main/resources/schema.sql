drop table if exists transactions;
drop table if exists category;
drop table if exists budget;

-- transaction
create table transactions (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    transaction_type VARCHAR(30) NOT NULL,
    date_posted TIMESTAMP NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    status VARCHAR(50) NOT NULL,
    memo VARCHAR(200),
    source VARCHAR(30) NOT NULL
);

-- budget
create table budget(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(200),
    created_at TIMESTAMP NOT NULL,
    total DECIMAL(19,4) NOT NULL,
    allocated DECIMAL(19,4) NOT NULL
);

-- category
create table category(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(200),
    created_at TIMESTAMP NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    budget_id uuid,
    CONSTRAINT budget_fk FOREIGN KEY (budget_id) REFERENCES budget(id)
);
