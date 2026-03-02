# Defination
- A view is a virtual table derived from one or more underlying tables (or other views). It doesn't store physical data but executes a predefined query to present data dynamically. Views simplify complex queries, enhance security, and abstract data structures.


# Types of views
- Simple

```sql
CREATE VIEW employee_view AS
SELECT id, name, salary 
FROM employees;
```


```sql
select * from employee_view
```

- Complex
```sql
CREATE VIEW sales_summary AS
SELECT o.order_id, c.name, SUM(o.amount) AS total 
FROM orders o
JOIN customers c ON o.customer_id = c.id
GROUP BY o.order_id, c.name;
```


- Materialized
* Stores Data in the physical Database
* Only Updates Data when refresh

```sql
CREATE MATERIALIZED VIEW monthly_sales_mv AS
SELECT product, SUM(revenue) 
FROM sales 
GROUP BY product
```

* update materialized view can be specify manually

```sql
REFRESH MATERIALIZED VIEW student_grades
```

* or by time
```sql
REFRESH MATERIALIZED VIEW student_grades EVERY 24 HOURS;
```

```sql
CREATE MATERIALIZED VIEW monthly_sales_mv AS
SELECT product, SUM(revenue) 
FROM sales 
GROUP BY product
REFRESH EVERY 24 HOURS;
```






```sql
select studnet_name,student_id,dept_id,dept_name
from Student inner join Depratment
on std_dept_id = dept_id



```





```sql
select employee_id,employee_name,count(num)
from employee
group by (employee_id)
```

```
1, Ahmed,1111
         2222
2, mohmaed, 3333
            4444         
```