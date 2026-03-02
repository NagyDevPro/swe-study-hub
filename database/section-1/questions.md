## Company Management System
- we have departments each department have(ID,name,director_name,director_email,) consists of many group-teams each group-team(ID,name,mail,number_of_teams,group-team-manager) have many teams each team (ID,name,mail,num_of_employees,team-manager) have many employees(ID,name,email,salary,grade,manager) each emloyee may exist on many teams but he is under only one department
each employee from a higher grade have insurane on his family(member_name,member_phone_number(multi phones))

* Map to erd diagram
* Map to tables diagram
* in your RDBMS create tables with inserting data into it










group-team(ID,name,mail,number_of_teams,group-team-manager)

team (ID,name,mail,num_of_employees,team-manager, parent_team_id)










```sql
-- Departments
CREATE TABLE Department (
  department_id   SERIAL    PRIMARY KEY,
  name            VARCHAR(100) NOT NULL,
  director_name   VARCHAR(100),
  director_email  VARCHAR(100)
);

-- Teams
CREATE TABLE Team (
  team_id          SERIAL    PRIMARY KEY,
  name             VARCHAR(100) NOT NULL,
  email            VARCHAR(100),
  number_of_units  INTEGER,
  department_id    INTEGER   NOT NULL
    REFERENCES Department(department_id),
  parent_team_id INTEGER NULL REFERENCES Team(team_id)    
);


-- Employees
CREATE TABLE Employee (
  employee_id       SERIAL    PRIMARY KEY,
  name              VARCHAR(100) NOT NULL,
  email             VARCHAR(100),
  
   direct_manager_id INTEGER NOT NULL
    REFERENCES Employee(employee_id)
);

-- M:N “exists in” between Team ↔ Employee
CREATE TABLE Team_Employee (
  team_id      INTEGER NOT NULL
    REFERENCES Team(team_id),
  employee_id  INTEGER NOT NULL
    REFERENCES Employee(employee_id),
  PRIMARY KEY(team_id, employee_id)
);
