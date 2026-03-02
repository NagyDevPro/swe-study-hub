# DDL: Data Defination Language
1. create
2. alter table
3. drop
4. truncate



## Create Query
```sql
CREATE TABLE employees (
  employee_id SERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL DEFAULT 'Ahmed',
  last_name VARCHAR(50),
  email VARCHAR(100) UNIQUE NOT NULL,
  hire_date DATE NOT NULL DEFAULT CURRENT_DATE,
  department_id INTEGER,
  CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(department_id)
);
```


## Postgresql Datatypes
1. Numeric Types
- Integer Types:
* INTEGER (or INT): 4 bytes.
* BIGINT: 8 bytes.
- Floating-Point Types:
* DOUBLE PRECISION: 8 bytes, double precision.
- Serial Types (auto-incrementing integers):
`SERIAL`, `BIGSERIAL`.

2. Character Types
`CHAR(n)` or **CHARACTER(n)**: Fixed-length string.
`VARCHAR(n)` or **CHARACTER VARYING(n)**: Variable-length string with a limit.
TEXT: Variable-length string with no limit. (Deprecated)

3. Date/Time Types
`DATE:` Stores calendar dates (year, month, day).
`TIME [WITHOUT TIME ZONE]:` Stores time of day (hour, minute, second).
4. Boolean Type
`BOOLEAN:` Stores TRUE, FALSE, or NULL.
9. UUID
UUID: Stores universally unique identifiers.

5. Binary Types
BYTEA: Stores binary data (e.g., images, files).
6. Geometric Types
Examples: POINT, LINE, LSEG, BOX, PATH, POLYGON, CIRCLE.
7. Network Address Types
Examples: CIDR, INET, MACADDR, MACADDR8.
8. JSON/JSONB
JSON: Stores JSON data as text.
JSONB: Stores JSON data in a binary format for faster querying.
10. Array Types
PostgreSQL supports arrays of any data type (e.g., INTEGER[], TEXT[]).
11. Range Types
Examples: INT4RANGE, NUMRANGE, TSRANGE, DATERANGE.
12. Specialized Types
XML: Stores XML data.
TSVECTOR and TSQUERY: Used for full-text search.
PG_LSN: Stores log sequence numbers.




## Alter table
1. add column
```sql
ALTER TABLE employees ADD COLUMN hire_date DATE;
```

2. delete column
```sql
ALTER TABLE employees DROP COLUMN hire_date;
```

3. rename a column
```sql
ALTER TABLE employees RENAME COLUMN lname TO last_name;
```

4. change type
```sql
ALTER TABLE employees ALTER COLUMN salary TYPE VARCHAR(20);
```

5. set a default value
```sql
ALTER TABLE employees ALTER COLUMN status SET DEFAULT 'active';
```

6. add primary key
```sql
ALTER TABLE employees ADD CONSTRAINT pk_emp PRIMARY KEY (employee_id);
```

7. add foreign key
```sql
ALTER TABLE employees
ADD CONSTRAINT fk_department
FOREIGN KEY (department_id)
REFERENCES departments (department_id);
```

## Drop
- Drops all table with it's structure
```sql
DROP TABLE employees;
```

## Truncate
- Drops the table data only
```sql
TRUNCATE TABLE departments CASCADE;
```


# DML : Data Manibulation Language
## insert
```sql
INSERT INTO employees (name, department, salary)
VALUES ('Alice Johnson', 'HR', 50000.00);
```

## Update
```sql
UPDATE employees
SET salary = 65000.00
WHERE name = 'Bob Smith';
```

## Delete
```sql
DELETE FROM departments
where id=10


DELETE FROM departments
where id=20

DELETE FROM departments
TRUNCATE FROM departments
```



## On constrain
used with foreign keys
possible values:
- CASCADE
- SET NULL
- SET DEFAULT

```sql
ALTER TABLE employees
ADD CONSTRAINT fk_department
FOREIGN KEY (department_id)
REFERENCES departments(id)
ON DELETE Set Default ;
ON UPDATE CASCADE;
```


* What will happen if
```sql
DELETE FROM employees;
```

* What is the difference between `delete` and `truncate`





# DQL: Data Query Language

```sql
SELECT pixel_2543 as hekaya_70,.... FROM table_name
```


```sql
SELECT * FROM employee where condition=10000
```

- Note: backend
frontend (Entity: data )


## Joins

- Cartisan product
```sql
SELECT emp_id,dept_id from employee,department
```

- inner join
```sql
SELECT e.name AS employee, d.name AS department
FROM employees e
INNER JOIN departments d
ON e.department_id = d.id;
```

- right outer join
```sql
SELECT e.name AS employee, d.name AS department
FROM employees e
RIGHT JOIN departments d
ON e.department_id = d.id;
```

- left outer join
```sql
SELECT e.name AS employee, d.name AS department
FROM employees e
LEFT JOIN departments d
ON e.department_id = d.id;
```

- full outer join
```sql
SELECT e.name AS employee, d.name AS department
FROM employees e
FULL JOIN departments d
ON e.department_id = d.id;
```
















- data employee , dept_name

```sql
select emp_id,emp_name,dept_name
where dept_id= id or emp_id is null
from employee, department 
```


data employee, manager_name
```sql
select emp.emp_id, emp.emp_name, manage.emp_name
where emp.manage_id = manage.emp_id
from employee emp, employee manage
```














## Aggregation Functions
- An aggregate function is a mathematical computation involving a range of values that results in just a single value expressing the significance of the accumulated data it is derived from


* SUM()
* MIN()
* MAX()
* AVG()

```sql
--! selects the minimum salary from table employees
select min(salary) from employee
```


```sql
select salary,count(id) from employee


select each salary for employee + count all ids
```









* Above query is wrong as mixing between aggregation and row data is completely syntax error

* GROUP BY() is used when 

```sql
select count(id)
from employee 
group by(salary)
where age>20
```










table employee,
id,name,age,salary


query:
select * from (select id from orders where status="pending") where time= sysdate

```sql
** nested query
select * from employee where salary = (select min(salary) from employee)

select id,name,salary from employee group by id
having min(salary)



select * from employee
order by salary
limit 1
```


















- dublicated employee names
ahmed 4
ali 5
mariam 2












```sql
-- please think well, will discuss the solutions after 5 minutes
select name,count(id) as dublicated from employee
group by name
having count(id)>1
order by dublicated,name desc

mariam 4
ahmed 4
mohamed 2
mamdouh 1

```


















