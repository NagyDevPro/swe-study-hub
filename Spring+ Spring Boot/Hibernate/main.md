# Hibernate Introduction And Main Feature

## Difination
Hibernate makes relational data visible to a program written in Java, in a natural and type-safe form.

- Hibernate Main Features: -
1. making it easy to write complex queries and work with their results,

2. letting the program easily synchronize changes made in memory with the database,

3. respecting the ACID properties of transactions,

4. allowing performance optimizations to be made after the basic persistence logic has already been written


## Main Features

### Jakarta Persistence (JPA) compatibility
- Right alongside its own "native" API, Hibernate provides an implementation of the `Jakarta Persistence specification`, the first JPA Was Influenced by `Hibernate 2`

- Hibernate can be easily used in any environment supporting JPA: Java SE standalone applications,`EE servers` like `WildFly`, and other containers including `Quarkus`.



### Idiomatic persistence

- Hibernate lets you write persistent classes following natural object-oriented idioms including **inheritance** and **polymorphism**, representing associations using standard Java collections.

 
  > * **persistent class**: is a regular Java class whose `objects` can be `saved` to (and retrieved from) a `database`. The state of these objects persists (survives) even after the application that created them has shut down.

- Hibernate does not require to implement an interface, extend a base class, or depend on any other intrusive framework-specific API, Just regular Spring provided annotations


### Tunable performance
- Hibernate provides flexible data fetching strategies, and an extremely-tunable two-level caching architecture. For managing concurrent data access.
  
  > - Hibernate have **Two Level Caching** for enhancing the level of fetching data from the database, `Level 1` Cache is related to each **Session** and it's meant to cache the entity comming from the database so if it was required it will be fastly retrived, **Note**: Level 1 Cache is related to each **Session** if the session was ended the cache ends. `Level 2` Cache is related to **Session Factory** which enhancing Cache of required entitys across **Multiable Sessions**, **Level 2 Cache is Disabled by default and is enabled by annotations**.
  > - even if Level 2 is enabled Hibernate still first check level 1 first then check level 2

- Hibernate by default is **Optimistic Locking** you can request it to be **Pesimistic Locking** or to be by timestamp.

- Most SQL is generated at system **initialization time** instead of at **runtime**.


  





### Scalability
- Hibernate was designed specifically for use in highly concurrent environments and in server clusters in lots of enterprise systems.


### Powerful query language
- **Hibernate Query Language(HQL)** is an incredibly powerful object-oriented dialect of SQL, with almost every feature of ANSI SQL SELECT statements you’ve ever heard of.

  > * HQL is specific query language for hibernate while **JPQL** is a standard query language for jpa(for any vendor than can implements JPA it must parse JPQL)

  > * JOOQ is slightley different than them thus it focuses on building sql queries from your java code and it maps it

- Since `Hibernate Processor` has access to your entities and their mapping annotations at compilation time, it’s able to `completely validate` the `syntax` and typing of a `HQL query` when you compile your Java code. 

> - `Hibernate Tools` and `IntelliJ IDEA` even provide automatic code completion and validation for `HQL queries` as you type

* so HQL Can check and validate queries in the compilation time but the main issue is that it still not type safe(all queries written in string not in types)

## Compatibility with a wide range of databases
- Hibernate is tested every day on PostgreSQL, MySQL, Db2, Oracle, SQL Server, Sybase ASE, EDB, TiDB, MariaDB, HANA, CockroachDB, H2, and HSQLDB. There’s even built-in support for Spanner.

> - Hibernate have a third party MongoDB Extension, it doesn't support it manually

- database driver is for communication while database dialect is for providing DBMS specific translations for hibernate or so.






























## To be checked/added
- what's optimistic locking.
- Hibernate consistently achieves superior performance compared to hand-written JDBC code??
- what's hibernate-processor??
- what's the difference below. 
hibernate-agroal

Support for Agroal connection pooling

hibernate-c3p0

Support for C3P0 connection pooling

hibernate-hikaricp

Support for HikariCP connection pooling




* Source:
https://hibernate.org/orm/

