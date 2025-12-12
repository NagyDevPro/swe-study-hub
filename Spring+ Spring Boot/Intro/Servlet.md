# Servlets


## Introduction

- In traditional web architecture, only static resources are provided.
- **Example**:- when we process a login, the `login page` is a static resource(means it's a standard page login.html).
- The issue is when we wanted to make it as **Dynamic** resource(the web server *Apache,Nginx,...etc* is able to run an exe program to handel the authenticaion of login).

- a web server needs an interface to request other programs, which is where the `CGI(Common Gateway Interface)` comes in.

- issue with CGI is that the server runs a new process per request.

- To solve this problem, **servlets** were developed

## Difination
**Servlets**: A servlet is a **Java program** that responds to **client requests**. It is a **web application component** that *dynamically* handles client requests, using `HTML` and `Java Threads` to respond.

- In the **MVC pattern**, servlets act as the **Controller**. In **Spring Boot**, the servlet is implemented as **Web Application Server**(WAS) using `Tomcat`

## Components
- **Servlet Container**: a web container that handles requests by mapping requests to each responsible servlet.

- **Servlet**: the class that handles a request.

- **Web.xml**: a configuration that guides `Servlet Container` to which Servlet this request should go.

- **HttpServletRequest Object, HttpServletResponse  Object**: Objects created by the servlet container that contains detailed information about reqeust and response, after your response, they will be deleted.



## Notes
- Tomcat, Jetty, Netty,...etc all considered as Servlet Containers that manages the lifecycle of the servlet including multi-threading

- The lifecycle of a servlet uses pooling methods like HikariCP to reduce the overhead of creating and deleting threads



## Sources
> *What is servlet?, a brief about servlets* https://medium.com/@bayazidfr/what-is-a-servlet-2abaa4203d7b 

> *History of servlets, Servlets in Spring boot* https://medium.com/@myggona/spring-boot-dispatcher-servlet-705353e3496a