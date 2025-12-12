# SOLID Principles in Object-Oriented Design

SOLID is an acronym for five key design principles that help developers write maintainable, scalable, and robust software.  

---

# S — Single Responsibility Principle (SRP)

## **Definition**
A class should have **one and only one reason to change**.  
It must do **only one job**, this is essential to avoid multi conditions and provides high maintainability.

## **Pros**
- Easier to maintain(No Multiable conditions) 
- Easier to test(as the class is responsible of one thing)  
- Higher reusability(you can use sub classes in multiable places, provides less size)

### **Example**

```java
// Bad: Class has multiple responsibilities
class OrderService {
    public void createOrder() { }
    public void sendEmailConfirmation() { }   // <- Not its responsibility
}

// Good: Split by responsibilities
class OrderCreator {
    public void createOrder() { }
}

class EmailNotifier {
    public void sendEmailConfirmation() { }
}
```

---

# O — Open/Closed Principle (OCP)

## **Definition**
Classes or Modules should be **open for extension but closed for modification**.  
You should be able to add new behavior without changing existing source code of the Class/Module.

## **Pros**
- Prevents breaking existing code
- Easily scalable  
- Better flexibility 
- Supports plugin-style architecture  

## **Example**

```java
// Bad: Modify the method every time a new shape is added
class AreaCalculator {
    public double calculate(Object shape) {
        if (shape instanceof Circle c) return Math.PI * c.radius * c.radius;
        if (shape instanceof Square s) return s.side * s.side;
        return 0;
    }
}

// Good: OCP via polymorphism
interface Shape {
    double area();
}

class Circle implements Shape {
    double radius;
    public Circle(double r) { this.radius = r; }
    public double area() { return Math.PI * radius * radius; }
}

class Square implements Shape {
    double side;
    public Square(double s) { this.side = s; }
    public double area() { return side * side; }
}

class AreaCalculator {
    public double calculate(Shape shape) {
        return shape.area();
    }
}
```

---

# L — Liskov Substitution Principle (LSP)

## **Definition**
Subclasses must be replaceable for their parent class without breaking the application.  
Objects of a superclass should be replaceable with objects of a subclass without altering correctness.

## **Pros**
- No unexpected subclass behavior  
- No `instanceof` checks  
- More reliable inheritance  
- Cleaner abstractions 

## **Example**

```java
// Bad: Penguin breaks the behavior contract (can't fly)
class Bird {
    void fly() { System.out.println("Flying"); }
}

class Penguin extends Bird {
    @Override
    void fly() { throw new UnsupportedOperationException(); } // breaks LSP
}

// Good: Separate behaviors
interface Bird { }

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    public void fly() { System.out.println("Sparrow flying"); }
}

class Penguin implements Bird { }
```

---

# I — Interface Segregation Principle (ISP)

## **Definition**
Clients must not be forced to depend on methods they do not use.  
Prefer many small interfaces over one large interface.

## **Pros**
- No unnecessary method implementations.  
- Cleaner API.
- Single Responsibility.
- More flexible design.

## **Example**

```java
// Bad: Fat interface forces unneeded methods
interface Worker {
    void work();
    void eat();
}

// Robots don't eat → forced dependency
class Robot implements Worker {
    public void work() { }
    public void eat() { throw new UnsupportedOperationException(); }
}

// ✔️ Good: Split interfaces
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    public void work() { }
    public void eat() { }
}

class RobotWorker implements Workable {
    public void work() { }
}
```

---

# D — Dependency Inversion Principle (DIP)

## **Definition**
High-level modules should not depend on low-level modules.  
Both should depend on abstractions.  

Abstractions should not depend on details.  
Details should depend on abstractions.

## **Pros**
- Easy to swap implementations  
- Easier testing (use mocks)  
- Reduced coupling  
- More maintainable architecture  

## **Example**

```java
// Bad: High-level depends directly on low-level class
class MySQLDatabase {
    void connect() { }
}

class UserService {
    private MySQLDatabase db = new MySQLDatabase(); // tightly coupled
    void loadUser() { db.connect(); }
}

// Good: depend on abstraction
interface Database {
    void connect();
}

class MySQLDatabase implements Database {
    public void connect() { }
}

class OracleDatabase implements Database {
    public void connect() { }
}

class UserService {
    private Database db;

    public UserService(Database db) {  // dependency injected
        this.db = db;
    }

    void loadUser() {
        db.connect();
    }
}
```