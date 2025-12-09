# Basics concepts and properties for hibernate

## Declration
- We can declare a dependency on this artifact

```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>7.1.9.Final</version>
</dependency>
```

## Hibernate Modules
- Hibernate ORM is broken into a number of modules with the intent of isolating transitive dependencies based on the features being used or not.

| Module               | Description                                                                                          |
|----------------------|------------------------------------------------------------------------------------------------------|
| hibernate-core       | The core object/relational mapping engine                                                            |
| hibernate-envers     | Entity versioning and auditing                                                                       |
| hibernate-processor  | Annotation processor that generates a JPA metamodel, plus optional Hibernate extras                  |
| hibernate-hikaricp   | Support for HikariCP connection pooling                                                               |
| hibernate-jcache     | Integration with JCache, allowing any compliant implementation as a second-level cache provider      |
| hibernate-graalvm    | Experimental extension to make it easier to compile applications as a GraalVM native image          |
| hibernate-micrometer | Integration with Micrometer metrics                                                                   |



## Hibernate Platform/BOM
- Hibernate also provides a platform (BOM in Maven terminology) module which can be used to align versions of the Hibernate modules along with the versions of its libraries


## Basic Configuration
onfiguration properties are specified in a file named `hibernate.properties`

```properties
# Database connection settings
hibernate.connection.url=jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1
hibernate.connection.username=sa
hibernate.connection.password=

# Echo all executed SQL to console
hibernate.show_sql=true   # If true, log SQL directly to the console
hibernate.format_sql=true # If true, log SQL in a multiline, indented format 
hibernate.highlight_sql=true # If true, log SQL with syntax highlighting via ANSI escape codes

# Automatically export the schema
hibernate.hbm2ddl.auto=create
```


## Entity Annotation
- means that this class considered as a table in the database
- This class nice to follow **JavaBean** Convention in **Setters** and **Getters** and feilds nice to be **private**
- Hibernate Requires your class to have at least one 