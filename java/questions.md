* how to make a class immutable?
- fianl class so other classes cannot extend
- no setters
- if the element is mutable return a copy of them not a element by itself


* String builder vs string buffer
- both are mutable
- String builder isn't thread safe, do not use syncronization
- String buffer is older but thread safe
- once per filter, a request should be handeled once per http request
- method level security, enables security per method level via annotations
- csrf, a server generates token with the state of changes to prevent melicsous sites to send your credientails with a request, we can disable it in spring security
- security context holder, manages security context
- security context, stores the current user logged in data
  


- hashTable is synchronized, while hashMap is not synchronized

- HashTable was introduced to handle keys that have large digits to store rather than using balanced tree.
- Elements in balanced tree are sorted, while in hashTable they are not sorted.


- super vs protected
- super is used to access the parent class members, while protected is an access modifier that allows access to the members of a class within the same package and subclasses in different packages.
- super resolves the issue if you have your own override or you have your own variables in the subclasses and you have the same in the super class and you want to use the one in the parent class.

- == vs equals
- == is used to compare the reference of two objects, while equals is used to compare the content of two objects. For example, if you have two string objects with the same content, == will return false because they are different objects in memory, while equals will return true because they have the same content.
> Most Classes in java override the equals method to provide a way to compare the content of objects, while the default implementation of equals in the Object class compares the reference of objects.




- when you define abstract method it should only be in an abstract class.
- Abstract method: is a method that is declared without an implementation.
- Abstract methods must be implemented by any concrete subclass of the abstract class.
- abstract classes can have **No** abstract method


- inner class: is a class that is defined within another class.
- look at below example of inner class
```java
public class OuterClass {
    private int outerField;

    public class InnerClass {
        public void accessOuterField() {
            System.out.println("Outer field value: " + outerField);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.accessOuterField();
    }
}
```
* Important note about inner classes:
* when you declare a refernce of the outerclass name as `OuterClass.InnerClass`, not `outer.InnerClass`, this is because when you declare a reference of the inner class, you are not referring to a specific instance of the outer class, but rather to the inner class itself. but when you create an instance of the inner class, you need to specify which instance of the outer class it belongs to.

- Static inner class: is a nested class that is declared static. It can be accessed without an instance of the outer class and does not have access to the instance variables of the outer class.
```java
public class OuterClass {
    private static int outerStaticField;

    public static class StaticInnerClass {
        public void accessOuterStaticField() {
            System.out.println("Outer static field value: " + outerStaticField);
        }
    }
}
```

- static inner class doesn't hold a reference to the outer class, so it is more memory efficient than the non-static inner class as if you create an instance of the non-static inner class, and it was called and active but the outer class instance wan't active, it won't be garbage collected because the inner class holds a reference to the outer class.

- anonymous inner class: is a class that is defined and instantiated in a single expression. It is often used to implement interfaces or extend classes without having to create a separate named class, think of an example if you want only to chagne a behaviour of the parent class you won't create a new class for that, you just can create an anonymous inner class and change the behaviour of the parent class.


```java
public class Main {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from the anonymous inner class!");
            }
        };
        runnable.run();
    }
}
```


- all variables inside an interface are final and static by default, and all of the methods are public abstract by defatult.
- interfaces attributes are final because they are constants, and interface doesn't have it's own heap memory.





- Functional interface: is an interface that has only one abstract method(single abstract method "SAM"), it can have multiple default and static methods, but it can **only have one** abstract method. `Functional interfaces` are used as the basis for lambda expressions in Java.
- Functional interfaces can be annotated with `@FunctionalInterface` to check at the compile time that this interface is intended to be a functional interface.

- Single Abstract Method was invented in java because of the need to have a way to represent functions as first-class citizens in java.

- lambda expressions: is a way to represent a function as on the fly, what is the mean of on the fly? it means that you can create a function without having to create a seprate class for it.

```java
Runnable runnable = () -> System.out.println("Hello from the lambda expression!");
runnable.run();
```




- Exceptions: Exceptions are incident events that happen at the runtime of a program distriput the normal flow of it due to error or unexpected behaviour.

Types of exceptions:
- Checked exceptions: are exceptions that are checked at the compilation time and must be handled by the programmer, why it must be handled? because checked exceptions can happen even if the code is correct **Due to external factors like network issues, file system errors, etc.** so the developer must have his second plan, also these exceptions are called `recoverable exceptions` because the programmer can recover from them, while the other type of exceptions **Unchecked exceptions** are issues due to bad code so they are "unrecoverable” when catching it doesn’t give you a meaningful alternative that preserves correctness, because it signals an issue in the code.

- Examples of checked exceptions: `IOException`, `SQLException`, `ClassNotFoundException`, `InterruptedException`, ``FileNotFoundException`, etc.
- examples of unchecked exceptions: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `IllegalArgumentException`, `ArithmeticException`, etc.



- Threads: a thread is a smaller unit of execution within a process.
- every thread in java should have `start` method to start the execution of the thread, and it should have a `run` method that contains the code that will be executed by the thread.
- Scheduling: the process of determining which thread will run at a given time is called scheduling, and it is handled by the operating system. Java provides two types of scheduling: `preemptive scheduling` and `cooperative scheduling`. In preemptive scheduling, the operating system can interrupt a running thread to give another thread a chance to run, while in cooperative scheduling, threads voluntarily yield control to each other.

> A cooperative thread continues running like normal code. It only stops being “the one running” when it hits a point like:

1. yield() / await (explicit “let others run”)
2. sleep(...)
3. waiting for I/O (read from disk/network)
4. waiting on a lock / condition / message queue

- Thread Priority: Java threads can have from 1 to 10 priority levels, 10 is the highest priority and 1 is the lowest, the default priority is 5. shceduler uses thread priority to determine which thread to run.

- Threads implements runnable interface. hence you can create a thread and pass it any object that implements the runnable interface, see below example:
```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello from MyRunnable!");
    }
}
public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }
}
```
- Thread States:
- Runnable: when a thread is ready to run but not running yet.
- Running: when a thread is executing its run method(started)
- Blocked: when a thread is waiting for a monitor lock to enter a synchronized block/method(sleep, wait, join).
- Terminated: when a thread has finished executing its run method or has been stopped(stopped).
- Notify: after the thread was blocked it can be notified to return to running.

- Thread Sleep vs Wait:
- Thread Sleep: acquires the lock and holds the object until it finishes sleeping.
- Thread Wait: releases the lock and allows other threads to acquire it, and it can be notified to return to running.



- DeadLock: is a situation where two or more threads are blocked forever, waiting for each other to release their resource locks.

DeadLock can be solved by:
- Two Phase Locking: where a thread acquires all the looks it needs before it starts executing and releases them all at once after it finishes.
- Lock Ordering: where all threads acquire locks in the same order, so that they cannot be blocked by each other.
- Lock timeout: where a thread will wait for a lock for a certain amount of time and if it cannot acquire it, it will give up and release any locks.
- Avoiding Nested Locks.

    