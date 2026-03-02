# Locks

## Meaning
- mechanism to prevent intervention between transactions by reserving resources for a time


## Types

**Shared Lock** (s-lock): lock this resource when writing only(no one can write, they can read)
- multiable transactions can have shared lock at the same time

**Exclusive Lock** (x-lock): lock this resource so no one can read or write it
- only one transaction can have the exclusive locking at the time

## Two Phase locking
- sometimes regular locking isn't serializeable because we can write on an object while it was just been unlocked.

- the two phase locking, depends on lock all resources you need to make operations on `Growing`, then unlock them when you don't need to, `Shrinking`

**Lock Point** is the peak point where all you're locking all your needed resourcess

**Strict Two phase locking** : never unlock the execlusive locked resources till the end.

**Strong Strict Two Phase Locking**: never unlock all resources that have been locked