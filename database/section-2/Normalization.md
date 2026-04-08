- full functional dependancy
- partial functional dependancy-->(composite key) --> can retirieve column data based on part of this composite key

- transitive functional dependancy






(Student_name, Subject) , Grade, Teacher, Teacher_grade

* types of keys
- foreign key
- primary key
- compoiste key


- pk ---> A,B,C,D














- Denormalization: the process of introducing redundancy into data base (make a step back from normalization) to improve performance, it is used when we have a lot of read operations and we want to reduce the number of joins between tables.





- Normal Forms: some of the rules that we should follow to make our database design better, Normal forms can also considered as set of guidelines that tests the database design and if it passed the test it is considered a good design.

- 1st Normal Form (1NF): each column should contain atomic one value at most no list of values, and we cannot use row order to represent data you should use another consistent way to represent data that depends on order, always define a primary key for each table.

- 2nd Normal Form (2NF): after 1NF, we should make sure that there is no partial dependancy "no column should be functionally dependent on part of the primary key".

- 3rd Normal Form (3NF): after 2NF, we should make sure that there is no transitive dependancy "no column should be functionally dependent on another column that is not a primary key".

- 4th Normal Form (4NF): after 3NF, we should make sure that there is no multi-valued dependancy "no column should be functionally dependent on another column that is not a primary key and it should not be a multi-valued dependancy". (this multi valued dependancy shouldn't be **trivial dependancy**)
 > - trivial dependancy: is a dependancy that is always true for any relation, for example if we have a relation R(A,B,C) and we have a dependancy A --> B then this dependancy is trivial because it is always true for any relation that has A and B as attributes.



- Fifth Normal Form (5NF): after 4NF, we should make sure that there is no join dependancy "the join dependancy is about the fact that we can have a relation that can be decomposed into two or more relations and we can join them back to get the original relation without losing any information".

  



