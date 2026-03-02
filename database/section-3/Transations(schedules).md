# Schedules

## Meaining
- rules to the order of multi operations comming from multi transctions to Ensure ACID in case of concurrent tarnsactions


## Conflicting operations

- operations comming from `different` transactions, `accessing` the same object,
- Ex: r1(x),w2(x) .... w1(y),r2(y) .... w1(x),w2(x)

## Recoverability
- Schedule that can be recovered in case of failure
* Rule: if T1 writes an object x before T2 reads it, then T1 must commit before T2(T Stands for tranction)

```sql
r1(x)
w1(x)   r2(x)
        w2(x)
        commit
commit

-- non recoverable as if we have failure on t1 we can't rollback as t2 has ended
```

```sql
r1(x)   r2(y)
        w2(y)
w1(x)   r2(x)
        w2(x)
commit
        commit

-- recoverable as we can rollback if failure
-- Note: if failure happen in t1 we must rollback t2 also as ther's dependancy
```


## Cascading Rollback
- once a parent transaction will be rollbacked or had any failure, then other child transactions must be rollback too

* Note that this operation is costy for a database

## Cascadeless Rollback Schedule
* Rule: If T1 writes on x before T2 reads x then T1 must commit before T2 reads x

```sql
r1(x)   r2(y)
w1(x)   w2(y)
commit
        r2(x)
        w2(x)
        commit

```




## Strict Schedule
* Rule: If T1 writes on x before T2 reads `or writes` x then T1 must commit before T2 reads x

```sql
r1(x)
w1(x)   r2(y)
        w2(y)    
commit    
        r2(x)
        w2(x)
        commit

```

## Serial Schedule
* Rule: Schedule that has each transaction done before the other transactions
- Have cost efficenty

```sql
r1(x)
w1(x) 
commit

r2(y)
w2(y)    
r2(x)
w2(x)
commit

```


## Serializable Schedule
* Rule: If a schedule conflict operations have been the same order matching to a serial schedule then this schedule considered serializable schedule

```sql
        r2(y)
r1(x)
        w2(y)    
w1(x)
r1(z)
r2(z)
        r2(x)
commit    
        w2(x)
        commit

-- serializable

```




```sql
r1(x)   r2(y)
        w2(y)
        r2(x)
w1(x)   
        w2(x)
commit
        commit

-- non-serializable                
```



