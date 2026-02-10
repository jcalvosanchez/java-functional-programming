# Introduction to Functional Programming

![Static Badge](https://img.shields.io/badge/date-2024-orange)

![Static Badge](https://img.shields.io/badge/software--architecture-purple)
![Static Badge](https://img.shields.io/badge/functional--programming-purple)

![Cover Image](cover_img/01-introduction-to-functional-programming.png)

## What is Functional Programming

In Computer Science, **[Functional Programming](https://en.wikipedia.org/wiki/Functional_programming)** is a programming paradigm that decomposes a problem into a set of Functions. A **[Function](https://en.wikipedia.org/wiki/Function_(computer_programming))** (also known as procedure, method or routine) is a unit of software logic that has a well-defined interface and behaviour and can be invoked multiple times.

- Programs are constructed by applying and composing functions.
- Functions are treated as first-class citizens, just as any other data type. This means that they can be passed as arguments to other higher-order functions, and return them as result.

This paradigm is in contrast with imperative programming, where a sequence of imperative statements update the running state of the program.

## Purely Functional Programming

A subset of Functional Programming is **Purely Functional Programming**, where **pure functions** are used, and therefore it is needed to **avoid mutable states** and **minimize side effects**.

### Pure Functions

A **[pure function](https://en.wikipedia.org/wiki/Pure_function)** is one that 

1. Has **Deterministic Behaviour**: Always produces the same output for the same input, regardless of external factors.
2. **Does not cause side effects**. A pure function does not modify external variables, mutate arguments, or interact with the outside world

### Data is Immutable

In order to enforce functions deterministic behaviour, **data is treated as immutable**: once data is created, it should not be modified. Instead, new data structures are created rather than altering the original.

### Minimize Side Effects

A **[Side Effect](https://en.wikipedia.org/wiki/Side_effect_(computer_science))** is any observable effect other than the primary effect of reading the value of its arguments and returning a value to the invoker of the operation: this occurs when a function modifies anything outside its scope or interacts with the outside world (e.g., writing to a file, changing a global variable, or printing to the console).

In Purely Functional Programming, side effects are minimized or isolated.

- **Avoid external dependencies**: Ensure the function relies only on its input arguments.
- If a function must perform side effects (e.g., saving to a database), then it is best to isolate that functionality in a separate, clearly defined function or even layer in order to ensure core logic remains pure.

### Main Benefits

- **Predictability**: Since Functions always produce the same outputs for the same inputs.
- **Easy to Test**: No side effects or mutable states make testing much more straightforward.
- **Turn complex problem into simpler ones**: Building complex functions by combining simpler ones.
- **Concurrency**: Immutability eliminates issues with shared mutable state in multithreaded environments.
- **Maintainability**: Avoiding side effects makes code more modular and reduces coupling.
- **Parallel programming**, due to avoiding mutable states and side effects, the problem can be divided into independent subsets that can be computed at the same time.

### Main Challenges

Most of real world application will require to be aware on **external factors** that will make functions return different outputs for the same inputs.

An example can be when my functions depends on an external web service

- This service can be up and running, or down
- The version of the service can changed
- The network connecting both can be down, a firewall can block the requests or responses...
- ...

## Some History

In recent years, functional programming concepts have been incorporated into multi-paradigm languages such as Java, Python, and JavaScript, making them more accessible to mainstream developers

But Functional Programming origins lie in academia, dating back in 1920s. 

- The first high-level language was [Lisp](https://en.wikipedia.org/wiki/Lisp_(programming_language)), developed in the late 1950s.
- [Haskell](https://en.wikipedia.org/wiki/Haskell) was released by 1990. Some dialects target the JVM.
- [Scala](https://en.wikipedia.org/wiki/Scala_(programming_language)) was released by 2004, runs in the JVM.
