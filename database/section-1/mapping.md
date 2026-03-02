ERD To DB Scheme Mapping (Best Practices)
- Step 1: Mapping of Regular Entity Types-->  regular table
    1. Mapping simple attribute --> regular column
    2. Mapping composite attribute --> regular columns
    3. Mapping multivalued attribute --> (New table + Composite PK of [parent + column])
    4. Mapping complex attribute --> (New table + Composite PK of [parent + columns])
    5. No Mapping for derived attribute 
- Step 2: Mapping of Weak Entity Types --> regular table that has (Composite PK[weak entitypartial key + regular entity pk ])

- Step 3: Mapping of Binary 1:1 Relation Types --> fk in any one of them if partial participation, if total participation they will be compined to a one table

- Step 4: Mapping of Binary 1: N Relationship Types. --> fk in the many

- Step 5: Mapping of Binary M: N Relationship Types --> (New table + Composite PK[pk1+pk2+ relation attr])

- Step 6: Mapping of N-ary Relationship Types. --> (New table + Composite PK [find yourunique combination])– Step 7: Mapping of Unary/Self Relationship. --> create new fk to the pk of the same table