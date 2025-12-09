## Common Interview questions

* What's the difference between `var`,`let`,`const`?
  - **var**: is not scoped variable, can be re-assigned, if hoisted it take `undefined`.
  - **let**: is scoped variable, can be re-assigned, if hoisted it isn't decalred, cannot access before declaration.

  - **const**: is scoped variable, `cannot` be re-assigned, if hoisted it isn't declared, cannot access before declaration.

* What's Hoisting??
  - **Hoisting**: is JavaScript’s behavior of moving declarations (variables and functions) to the top of their containing scope (global or function) during the compilation phase before code execution. 
  - Variables declared with `var` are hoisted and automatically initialized to `undefined`.
  - Variables declared with `let` and `const` are hoisted but not initialized (`TDZ` ).
  - `Function declarations` are fully hoisted, so you can call them before their declaration in code.


* What is Event Loop and What's the difference between `microstack` and `macrostack`.
  - Javascript is `single threaded` which means that one process runs at a time, the `nodejs runtime` or the `browser` provides concurrency between multiable threads by concurrency between them. 

  - the mechanisim that handles concurrency is called Event loop.
  * **Components**:-
    - `Call Stack`: Where currently executing frames (functions) live.
    - `Heap`: Memory for objects.
    - `Task Queues`: Collections of callbacks waiting to run.
  * **Event loop execution**:-
    - first check the call stack if it's empty.
    - execute the macrotasks.
    - then, check if there's any microtasks(try,catch, promise,...etc).
    - if microtasks are empty. then go to the task queues.

* What is promise?
  - A Promise in JavaScript is an `object` that represents the `eventual result (or failure)` of an `asynchronous` operation. It’s a `placeholder` for a value that isn’t yet available but will be resolved (or rejected) at some point in the future.
  - States: Pending, fullfilled, rejected.
 