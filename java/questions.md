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