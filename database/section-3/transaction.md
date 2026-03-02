# What is Transaction

- Known as multi operations collected in a single operation
- happen in systems like banking, e-shop,...etc

```sql
UPDATE accounts SET balance = balance - 100.00
    WHERE name = 'Alice';
UPDATE branches SET balance = balance - 100.00
    WHERE name = (SELECT branch_name FROM accounts WHERE name = 'Alice');
UPDATE accounts SET balance = balance + 100.00
    WHERE name = 'Bob';
UPDATE branches SET balance = balance + 100.00
    WHERE name = (SELECT branch_name FROM accounts WHERE name = 'Bob');
```



```sql
BEGIN;
UPDATE accounts SET balance = balance - 100.00
    WHERE name = 'Alice';
UPDATE accounts SET balance = balance + 100.00
    WHERE name = 'Bob';
UPDATE accounts SET balance = balance + 100.00
    WHERE name = 'Wally';
COMMIT;
```


# Save Point

```sql
BEGIN;
UPDATE accounts SET balance = balance - 100.00
    WHERE name = 'Alice';
SAVEPOINT my_savepoint;
UPDATE accounts SET balance = balance + 100.00
    WHERE name = 'Bob';
-- oops ... forget that and use Wally's account
ROLLBACK TO my_savepoint;
UPDATE accounts SET balance = balance + 100.00
    WHERE name = 'Wally';
COMMIT;
```


## Transaction properities
ACID

### Automacity
- Each transaction must happen in all-or-nothing


### Consistency
- Means to be consistent to database constrains in case of before transaction,
and after the transaction

- consistency as data integrity
- consistency as values

database replication


- some big databases have eventual consistency due to replication

## Isolation
- Each transaction must be done alone

- Concurrent Transactions may interleave each other, causing three main problems
1. Lost Update
2. Dirty Read
3. Non-repeatable read


- Hence there's multi level of isolation
1. Serializeable: each transaction must be done alone without dirty read
2. Repetable read: each transaction reads values once from the commited values in the beggining of the transaction
3. Read commited: each transaction reads only commited values
4. Read uncommited: transaction varaibles can read any value


## Durability
- once the data is commited it must be recovered whenever any failure happens
- in memory databases (redis) only with ram





























```sql
update sduent deduct student_balance

save point savepoint1



declare 10 variables
assign to variables


rollback to savepoint1



```
