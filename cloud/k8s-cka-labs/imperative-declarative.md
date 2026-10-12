kubectl api-resources (lists all of the available resources in the cluster, including their "kind, name, shortnames,...etc").


kubectl explain <resource> (provides top-level information about a specific resource, including its fields).

kubectl explain <resource>.<field> (provides detailed information about a specific field within a resource, including its type and description).


`kubectl explain <resource> --recursive` (provides detailed information about all fields within a resource, including nested fields).



