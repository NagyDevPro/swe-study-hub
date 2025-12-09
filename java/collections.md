# Collections

## Defination

- **Collection API** : A native interface that have list of methods in order to handle multiable data

## Collection Implementation

- Are interfaces that *Extends* Collection interface:
1. Lists
2. Sets
3. Queues


## Collections

- Are Classes that implements *Interfaces* of Collection Api

### List Collection

1. **ArrayList**: Dynamically allocation(Dynamic size) list that can have indexing, implements the *List* interface and allows `nullable` values 

**Example**:-
```java
List<Integer> arr = new ArrayList<Integer>();

arr.add(10);
arr.add(20);

System.out.println(arr) //list will be printed
```

**Operations Complexity**:-

| Operation      | Time Complexity     | Notes                |
|----------------|----------------------|-----------------------|
| get(index)     | O(1)                 | Fast access           |
| add(element)   | O(1) (amortized)     | Append at end         |
| add(index, e)  | O(n)                 | Elements shift right  |
| remove(index)  | O(n)                 | Elements shift left   |


**Important Notes**:-

* When array list is full of size and you want to enter more elements a `Resizing` operation will be happen
* Initial capacity 10 and it grows by `50%` when capacity is full`(On Resizing)`. 
* Resizing involves creating a new array and copying elements
* Not suitable for multi-threading (use `Collections.synchronizedList()` or 
`CopyOnWriteArrayList` if needed)
* Faster than LinkedList in access
* Slower than LinkedList in insertion/deletion in the middle



### Set Collection

1. **HashSet**: *Unorderd Unique* list of elements, where element are stored based on *hashing*

- hashing methodology
```java
int hashcode = num.hashcode() //hashcode outputs the same number

//index will be
int index= hashcode % numOfBuckets // where num of buckets here is refered to the size of the hashset
```

- sets
```java
Set<String> myset = new HashSet<String>()

myset.add("mahmoud")
myset.add("mahmoud")
myset.add("tamer")
// myset elements = ["mahmoud",""tamer]

```

2. **TreeSet**: *Ordered Unique* list of elements, extends `NavigableSet` which extends from `SortedSet`




# Map
