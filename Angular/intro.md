- Single Page Application (SPA) is a web application that loads a single HTML page and dynamically updates the content as the user interacts with the app. Angular is a popular framework for building SPAs.


- Angular uses TypeScript, a superset of JavaScript, which provides static typing and other features to enhance development experience.

- Angular consists of components.
- Componenet consists of three main parts:
  - Template: defines the HTML structure and layout of the component.
  - Class: contains the logic and data for the component.
  - Styles: defines the CSS styles for the component.

- Directives are special DOM(Template) modifiers that can be used to manipulate the DOM in Angular, based on the data in the component.ts
e.g. *ngFor, *ngIf, etc.
* Directive Types:
  * Structural Directives: Change the structure of the DOM by adding or removing elements. Examples include `*ngIf` and `*ngFor`.
  * Attribute Directives: Change the appearance or behavior of an element, component, or another directive. Examples include `ngClass` and `ngStyle`.


- Pipes: pipelines used to transform the data to an appropriate formate
> Directives and Pipes are **used in the template** to manipulate the DOM and transform data respectively.

- **Service:** A service is a class that provides *specific* functionality and can be *shared across components*. 
- Services are typically used for tasks such as data fetching, business logic, and state management.

- Modules: *Angular before 17*, every component, directive or pipe must be declared in excactly one module. After *Angular 17* we can have *standalone components*.

- Decorators: Decorators are used to add metadata to classes, properties, methods, or parameters. They are a core feature of Angular and are used to define components, directives, services, and more. Examples include `@Component`, `@Directive`, `@Injectable`, etc.

- Angular.json: is the configuration file for all angular css,scripts and assets.

- main.ts: is the entry point of the application, where the root module is bootstrapped.

- selector: is the name of the HTML tag that represents the component in the template, Angular uses the selector to identify where to render the component in the DOM.

```ts
@Component({
  selector: 'app-root', // this component will be rendered in any html element with the attribute app-root

  vs

  selector: '[app-root]', // any html element with this attribute.

  template: `` //inline html code
  templateUrl: "" // url of the html template page
```

- each component have it's own styles, but we can also have global styles in styles.css file.

- typeScript types
    - any: can be any type, it disables type checking for that variable.
    - unknown: can be any type, but you need to perform type checking before using it.
    - never: represents a value that never occurs, often used for functions that throw exceptions or have infinite loops.
  ```ts
    let a: any = 5; // can be reassigned to any type
    let b: unknown = 5; // must be type-checked before use
    let c: never = 5; // this will cause a compile-time error
  ```
  - Constructors in typescript.
    ```ts
        class Person {
        name: string;
        constructor(name: string) {
            this.name = name;
        }
        }
    ```


- typescript has type casting, which allows you to convert a variable from one type to another.
    ```ts
        let a: any = "Hello";
        let b: string = a as string; // type casting from any to string
        //or
        let c: string = <string>a; // type casting using angle brackets
    ```
> in ts if type was `any` you can't use any **functionality** of the variable without type casting.


- type script can auto detect the type of a variable based on the assigned value.
    ```ts
        let x = 5; // x is inferred to be of type number
        let y = "Hello"; // y is inferred to be of type string
    ```
- typescript have union types, which allow a variable to hold more than one type of value.
    ```ts
        let z: number | string; // z can be either a number or a string
        z = 5; // valid
        z = "Hello"; // valid
    ```

- typescript has access modifiers: public, private, protected.
    - public: can be accessed from anywhere.
    - private: can only be accessed within the class.
    - protected: can be accessed within the class and its subclasses.
    ```ts
        class Person {
        public name: string; // can be accessed from anywhere
        private age: number; // can only be accessed within the class
    ```    
- Angular String interpolation: is a way of binding data from the component to the template using double curly braces `{{}}`.
    ```html
        <h1>{{ title }}</h1> <!-- title is a variable in the component -->
    ```

- Binding types:
    1.  One Way Binding
    - Interpolation: `{{}}` used
    - Property Binding: `[property]="expression"` used to bind a property of an HTML element to a variable in the component.
    - Event Binding: `(event)="handler"` used to bind an event of an HTML element to a method in the component.
    
    2. Two Way Binding.
    - Two-way Binding: `[(ngModel)]="property"` used to bind a property in the component to an input element in the template, allowing for two-way data binding.


- interfaces in ts: are used to define the structure of an object, they are not compiled to JavaScript and are only used for type checking during development.
    ```ts
        interface Person {
        name: string;
        age: number;
        }

        // Example of using the interface
        let person: Person = {
        name: "John",
        age: 30
        };
    ```

- why we are using interfaces in models?
  - Interfaces provide a clear contract for the shape of data, making it easier to understand and maintain the code.
  - They enable better type checking and autocompletion in IDEs, which can help catch errors early in the development process.
  - They allow for better code organization and separation of concerns, as the interface can be defined separately from the implementation of the model, making it easier to manage and update the codebase.

- how to define attributes in component class without initializing them?
  - You can use the `!` operator (definite assignment assertion) to tell TypeScript that the variable will be assigned a value later, even if it is not initialized at the point of declaration.
    ```ts
        class MyComponent {
        myVariable!: string; // This tells TypeScript that myVariable will be assigned a value later
        }
    ```


    ```ts
        class MyComponent {
        myVariable: string; // This will cause a compile-time error because myVariable is not initialized

        constructor() {
            this.myVariable = "Hello"; // Now myVariable is assigned a value in the constructor
        }

        setMyVariable(value: string) {
            this.myVariable = value; // You can also assign a value to myVariable in a method
        }
        // in this way you can assign a value to myVariable by calling setMyvariable or you can use myVariable= "value"

        set MyVariable(value: string) {
            this.myVariable = value;
        }
    ```



