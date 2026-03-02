# Strings

## Introduction
- In Java, a `String` is a sequence of characters. It is a widely used data type for representing text.
- Strings in java are immutable, which means that once a `String` object is created, it cannot be changed. Any operation that seems to modify a string actually creates a new string object.

> Why Strings are immutable in Java?
   - **Security**: Strings are often used to represent sensitive data such as passwords, so making them immutable helps prevent data corruption.
   - **Performace**: Immutable objects can be shared accross multiple threads without synchronization.
   - **Hashcode Reliability**: Strings are commonly used as keys in HashMap; immutability ensures the hashcode remains consistent.
   - **Memory Efficiency**: The JVM can optimize memory usage by reusing string literals from the string pool.
   - **Hashcode Caching**: As the hashcode of a string is called many times, caching it can improve performance, as strings are immutable, the hashcode can be calculated once and stored for future use.


## String Pool
- The `String Pool` is a special area of memory in the Java heap that stores string literals. When a string literal is created `String s = "Hello";`, the JVM checks the string pool to see if an identical string already exists. This optimization helps save memory and improve performance when working with strings.

## StringBuilder and StringBuffer
- `StringBuilder` and `StringBuffer` are mutable classes used for creating and manipulating strings. They provide methods for appending, inserting, and modifying strings without creating new objects.
- `StringBuilder` is not thread-safe, while `StringBuffer` is thread-safe.
- `StringBuffer` gives you initially 16 bytes, with variable size(increased when you append more characters)
- see below example
  ```java
  StringBuffer sb = new StringBuffer("Hello");
  sb.append(" World");
    System.out.println(sb); // Output: Hello World
    System.out.println(sb.capacity()); // Output: 21 (initial capacity of 16 + length of "Hello World" which is 11)
    System.out.println(sb.length()); // Output: 11 (length of the string "Hello World")
  ```

- `StringBuilder` is more modern and is generally preferred over StringBuffer when thread safety is not a concern, as it offers better performance due to the lack of synchronization overhead.


- 








