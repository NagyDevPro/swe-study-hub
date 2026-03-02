# ERD: Entity Relationship Diagram

## Defination
- is a mapped objects that represents business objects and their relationship
- ERD is a represental shape of the business design
- ERD is mapped to tables by `applying set of rules`
- after applying these tools then we will use *RDBMS* (Relational Database Management System)



## Types of Entites
- Strong Entity: an Entity that have a primary key
- Weak Entity: an Entity that doesn't have a primary key (often related to another entity)


## Types of Attributes
- simple attribute
- composite attribute: contains of multi attributes
- multi valued attribute: attributes that has multi values (double ovals)
- drived attributes: dashed attributes
- complex attribute: multi valued + composite

## Types of relationships
- unary(or self relation): two types of the same type
- binary: between two entites
- ternary

# relationships has many cardinalities(cardinality ratio)
- one to one
- many to one: 
- many to many: in a seprate table

# Relationship participation (participation constrain)
- total participation(must have, at least)
- partial participation(might have, at most)

## Types of keys
- primary key: single unique key used to identify the entity
- partial: with weak entities, a key to be assigned with it's strong entity to ensure uniqueness.
- composite: compose of multi keys
- candiate key: simple unique value that might be a primary key


