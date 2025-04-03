---- initialize test  database
insert into budget(id, name, created_at, total, allocated) values ('24bf4a61-f801-4de8-aa68-651ca0e1986b', 'test', '1970-01-01 00:00:00', 10, 0)

insert into category(id, name, created_at, amount, budget_id) values ('6f1b7575-cb5e-4114-9ca1-32356a0e78f7', 'test', '1970-01-01 00:00:00', 10, '24bf4a61-f801-4de8-aa68-651ca0e1986b')
