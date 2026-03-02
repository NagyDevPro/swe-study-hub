# File Systems

- an old methodology to manage data

```java
import java.nio.file.*;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import static java.nio.file.StandardOpenOption.*;

public class ReadWriteModesExample {

    public static void main(String[] args) {
        Path input  = Paths.get("data/input.txt");
        Path output = Paths.get("data/output.txt");

        // 1. READ: read all lines into a List<String> (uses READ mode by default)
        List<String> lines;
        try {
            lines = Files.readAllLines(input, Charset.forName("UTF-8"));
            System.out.println("Read " + lines.size() + " lines from " + input);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e);
            return;
        }

        // 2A. WRITE mode (CREATE + TRUNCATE_EXISTING): overwrite or create fresh
        try (BufferedWriter writer = Files.newBufferedWriter(
                output,
                Charset.forName("UTF-8"),
                CREATE,            // create file if it doesn’t exist
                TRUNCATE_EXISTING  // if it does exist, discard previous content
        )) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.write("=== Finished writing in OVERWRITE mode ===");
            writer.newLine();
            System.out.println("Wrote (overwrite) to " + output);
        } catch (IOException e) {
            System.err.println("Failed overwrite write: " + e);
        }

        // 2B. APPEND mode (CREATE + APPEND): add to end or create fresh
        try (BufferedWriter writer = Files.newBufferedWriter(
                output,
                Charset.forName("UTF-8"),
                CREATE,  // create if missing
                APPEND   // append rather than overwrite
        )) {
            writer.write("=== Appending another line ===");
            writer.newLine();
            System.out.println("Appended to " + output);
        } catch (IOException e) {
            System.err.println("Failed append write: " + e);
        }

        // 2C. STRICT WRITE mode (WRITE only): fails if file doesn’t exist
        Path strict = Paths.get("data/strict.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(
                strict,
                Charset.forName("UTF-8"),
                WRITE    // open for write but do NOT create
        )) {
            writer.write("This will crash if strict.txt didn’t exist beforehand.");
            writer.newLine();
        } catch (NoSuchFileException nsf) {
            System.err.println("strict.txt not found (WRITE only) — no CREATE flag used.");
        } catch (IOException e) {
            System.err.println("Failed strict write: " + e);
        }
    }
}

```

## Database
- databases are centerlaized data storage that can have different features: -
* Data integrity
* RelationShips
* Uniquie of data
* Infrastructure management

## RDBMS: Relational database management systems
- systems that are used to construct database
- RDBMS can be: 
* Oracle
* Postgresql
* SqlServer
* Mysql (Maria db)

- Each RDBMS must follow standard sql
* DDL:
create, alter table, drop
```sql
# create query
CREATE TABLE employees (
    employee_id     INT             PRIMARY KEY,
    first_name      VARCHAR(50)     NOT NULL,
    last_name       VARCHAR(50)     NOT NULL,
    email           VARCHAR(100)    UNIQUE NOT NULL,
    hire_date       DATE            DEFAULT CURRENT_DATE,
    salary          DECIMAL(10,2)   CHECK (salary > 0),
    department_id   INT,
        CONSTRAINT fk_department
        FOREIGN KEY (department_id)
        REFERENCES departments(department_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

```
```sql
ALTER TABLE employees
ADD CONSTRAINT fk_department
  FOREIGN KEY (department_id)
  REFERENCES departments(department_id)
  ON UPDATE CASCADE
  ON DELETE SET NULL;

```
```sql
DROP TABLE employees
```

* DML
insert, update, delete

* DQL
select

* DCL: grant, revoke
```sql
-- Grant one or more privileges on an object to a user (or role)
GRANT
    privilege_list
ON
    object_name
TO
    grantee
[ WITH GRANT OPTION ];       -- (optional; allows grantee to pass the same privileges on



-- Revoke one or more privileges from a user (or role)
REVOKE
    privilege_list
ON
    object_name
FROM
    grantee
[Cascade | Restrict]    
```


- although RDBMS follow standard sql they vary in features and functions **which make it hard to migrate from DBMS to another DBMS**


- when creating database tables, we lack orginazation of tables