# Spring Security

## Introduction

### Importance of Security
In today's digital landscape, securing applications is paramount. Spring Security is a powerful and customizable authentication and access-control framework for Java applications, but the main question is how to know the types of attacks?

### OWASP Top 10
- The OWASP Top 10 is a regularly updated report outlining security concerns for web application security, focusing on the 10 most critical risks.

- The report is put together by a team of security experts from all over the world.

- OWASP refers to the Top 10 as an ‘awareness document’ and they recommend that all companies incorporate the report into their processes in order to minimize and/or mitigate security risks



### High Level of architecture of Spring Security

- **Filter**: Spring Security’s Servlet support is based on Servlet Filters,
A `Filter` is an object that performs filtering tasks on either the request to a resource (a servlet or for example) or on the response from a resource, or both.


> **Servlet**: In a Spring MVC application, the `Servlet` is an instance of `DispatcherServlet`. At most, **one** Servlet can handle a single `HttpServletRequest` and `HttpServletResponse`.


- See more about filters in **filters.md**



## Sources

> [OWASP Top 10 official documentation](https://owasp.org/www-project-top-ten/)

> [What's OWASP 'A Beautiful Article'](https://www.cloudflare.com/learning/security/threats/owasp-top-10/)

> [Servlet Filters official documentation](https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-architecture)


> [Spring Security official documentation](https://docs.spring.io/spring-security/reference/index.html)





## To Search About
- What is Servlet?
- What is Filter?
- What is CSRF?
- How CSRF Token is generated and how it works?
- in poostman what will happen if i've didn't send authentication and only sent CSRF token?
- What is CORS?
- What is The Servlet Filter Chain?
- Spring Security Default Configuration and application.properties
- From postman if i logged in using basic auth how postman maintain the session?