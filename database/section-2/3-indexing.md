# Indexing

- A structure that used for Optimizing Query (especially in retrivals) instead of table scan




# without indexing
- high performance in insert,delete
- low performance in selecting

# with index
- high performance in selecting data
- low performance in insert,delete


## General Index Algorithms:

- B-Trees
- B+ Trees

- Hashing

- Bitmap
- composite


## Clusterd Index
- **Clustered Index** : A clustered index determines the physical order of the data rows in a table. The table’s data is stored on disk in the same order as the index. Because the physical order of the data can only be arranged in one way, a table can have only one clustered index. For example, if you create a clustered index on an "employee ID" column, the rows in the table are physically sorted by employee ID.


- **Non-Clustered Index**: A non-clustered index is a separate structure from the table’s data, containing a copy of the indexed columns and pointers to the actual data rows. The physical order of the data in the table remains unaffected by the index, allowing you to have multiple non-clustered indexes on a single table. For instance, a non-clustered index on "last name" would store last names and references to the rows, while the table’s data could still be ordered by something else (like a clustered index on employee ID)


## Advantages
- fast retrive of data

## disadvantages
- costly insertions and deletions


