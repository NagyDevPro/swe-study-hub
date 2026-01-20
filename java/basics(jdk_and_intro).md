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

## Questions to ask
* what's the difference between `JDK`, `JVM`, `JRE`.
* what are the contents of `JDK` and `JVM`.


