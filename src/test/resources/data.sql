---- initialize test  database
insert into budget(id, name, created_at, total, allocated, frequency) values ('24bf4a61-f801-4de8-aa68-651ca0e1986b', 'test', '1970-01-01 00:00:00', 10, 0, 'ONCE')

insert into category(id, name, created_at, amount) values ('6f1b7575-cb5e-4114-9ca1-32356a0e78f7', 'test', '1970-01-01 00:00:00', 10)

insert into transactions (id, created_at, transaction_type, date_posted, amount, status, memo, budget_id, category_id) values ('0b27873a-58a2-40f2-9136-166c6ebd4f82', '1970-01-01 00:00:00', 'CREDIT', '1970-01-01 00:00:00', 10, 'NEW', 'memo', '24bf4a61-f801-4de8-aa68-651ca0e1986b', '6f1b7575-cb5e-4114-9ca1-32356a0e78f7')
