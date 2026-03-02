# Java_Core (JDK and Introduction)

## JVM
- Known as `Java Virtual Machiene`, the platform that responsible for running your java code

* JVM is platform `dependant` although Java itself is platform `Independant`.

> Hence JVM is platform independant **pointers** aren't allowed in Java, because pointers are platform dependant, as pointers are very specific to the memory addresses and architecture of the underlying system.


* JVM only understands `byte code` which is converted when you compile your `java code` through your compiler `javac`.
* The extension for a byte code is `.class` file
* to run your java code you have to use `java <ClassName>`

* `JRE` is installed with `JDK`.



## JRE
- Known as `Java Runtime Environment`, it is a package of libraries and software that are required to run Java applications.
* JRE contains `JVM` and `core libraries` to run java applications.
* JRE does not contain development tools like `compiler` and `debugger`.
* JRE usually used by end-users to run Java applications.
* JRE is platform `dependant` as it contains JVM.

> Java Application is complied to byte code which executed by JVM inside JRE this process called dynamic linking libraries at runtime.


## JDK
- Known as `Java Development Kit`, it is a software development kit used to develop Java applications.
- JDK contains `JRE` and development tools like `compiler (javac)`, `debugger (jdb)`, `JavaDoc`, etc.
- JDK is used by developers to write, compile, and debug Java applications.






## Java Memory Allocation
- The `JVM`’s memory is part of the **system’s RAM**. When the `JVM` starts, it **reserves a portion** of RAM for its own memory management, which is where it runs Java applications.

- This reserved memory is organized into different areas to efficiently manage the lifecycle of data during program execution. The main memory spaces are:
  
  1. `Stack Memory`: generally **limited** in size, used to store method calls and local variables.
  * Each thread has its own stack memory, and it is used for static memory allocation.
  * **Memory Allocation and Deallocation:** Memory for a method is allocated when the method is called and deallocated when it finishes execution.
  * Each time a method is called, a new block of memory is created including:
     - Method parameters: variables passed to the method.
     - Local variables: variables declared within the method `primitive` and `reference` types.
     - Return address: the point in the program to return to after the method finishes(this is used for method calls and return values).

  2. `Heap Memory`: used to store objects and class instances created during the execution of a Java program.
      - Heap memory is **shared** among all threads in the JVM.
      - Memory Heap is used for dynamic memory allocation.
      -  `User Defined Objects (e.g., instances of user customer classes)`, `Java Built-in Objects (e.g., Strings, Arrays)`, `String Pool` are stored in heap memory.
      -  `Garbage Collector` is responsible for automatically freeing up memory in the heap that is no longer in use by the program.
  
   > **String Pool**: String literals are stored in a special area of the heap called `String Pool` to optimize memory usage and improve performance. When a user writes `String str = "Hello;"`, the JVM first checks the String Pool in order to forbidden creating dublicated and multiable string objects.

 3. `Metaspace`: This is where the JVM stores class metadata, such as class definitions and method information. It is used for class loading and reflection.
     - Metaspace is **not part of the heap** and has its own memory management system.
     - Metaspace can grow dynamically as needed, and it is not limited in size like the heap. However, it can lead to `OutOfMemoryError` if it grows too large.
     - Class metadata includes information about the `class structure`, such as `method signatures`, `field definitions` This information is used by the JVM to execute Java code and perform operations like method invocation and field access.
     - Metaspace can also store static variables and constants defined in classes.
     - Runtime constant pool: part of the Metaspace that stores constants and literals used by the class, such as string literals and numeric constants.
     - Synchronization: The JVM handles synchronization when accessing the Method Area to prevent inconsistencies and ensure thread safety.
   

## Now you have a conflict between the string literals that are stored in the metaspace and the string literals that are stored in the heap, which one is used when you create a string literal in your code?
- When you create a string literal in your code, it is stored in the `String Pool` which is part of the `Heap Memory`. The `Metaspace` is used to store class metadata and does not directly store string literals. However, the `Runtime Constant Pool` within the Metaspace can contain references to string literals that are used in the class, but the actual string objects are stored in the String Pool in the heap.


## Static Block
- A `static block` is a block of code that is executed when the class is loaded into memory. It is used to initialize static variables or perform any setup that needs to be done before the class can be used.

- Static blocks are stored in the `MetaSpace` memory area of the JVM, which is where class metadata and static variables are stored.









## JVM Memory Types
- `Heap Memory`: This is where objects are stored in memory. It is shared among all threads and is used for dynamic memory allocation.
- `Stack Memory`: This is where method calls and local variables are stored. Each thread has its own stack memory, and it is used for static memory allocation.




## Questions to ask
* what's the difference between `JDK`, `JVM`, `JRE`.
* what are the contents of `JDK` and `JVM`.


