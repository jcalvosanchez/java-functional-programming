# Mastering `Optional` in Java: Eliminate Nulls Enhancing Code Readability

![Static Badge](https://img.shields.io/badge/date-2025-orange)

![Static Badge](https://img.shields.io/badge/software--architecture-purple)
![Static Badge](https://img.shields.io/badge/functional--programming-purple)

![Cover Image](cover_img/07-java-optional.png)

## Nulls in Java

`null` in Java is  a special value that represents the absence of a reference to any object in memory at runtime.

This brings two main problems when coding in Java

### Null-safety

Any object and variable can potentially hold a `null` value at **runtime**. When accessing an object that has a `null` value, a runtime exception (`NullPointerException`) will be thrown interrupting the normal flow of the program. This might lead to unpredictable behaviour during runtime.

For example, the following code is **not null-safe** 

```java
object.doSomething();
```

Therefore the code is unpredictable at runtime:

- when `object` is not `null`, then it will `doSomething`
- when `object` is `null`, then it will not `doSomething` and will interrupt the flow with a `NullPointerException`.

To make your code null-safe you need to explicitly check for `null` before accessing any object, which also increases the complexity and makes your code much more verbose

```java
if (object != null) {
    object.doSomething();
}
```

To illustrate this problem with some real code:

```java
public record Address(String street, String city) {}

Function<Address, String> composeAddress = (address) -> address.street().concat(" ").concat(address.city());
```

Will have the following behaviour at runtime

```java
@Test
public void testNullsInJava() {
    Function<Address, String> composeAddress = (address) -> address.street().concat(address.city());
    Address address = null;
    assertThrows(NullPointerException.class, () -> address.street());
    assertThrows(NullPointerException.class, () -> composeAddress.apply(null));
    assertThrows(NullPointerException.class, () -> composeAddress.apply(new Address(null, null)));
    assertThrows(NullPointerException.class, () -> composeAddress.apply(new Address("street", null)));
    assertThrows(NullPointerException.class, () -> composeAddress.apply(new Address(null, "city")));
    assertDoesNotThrow(() -> composeAddress.apply(new Address("street", "city")));
    assertEquals("street city", composeAddress.apply(new Address("street", "city")));
}
```

### Lack of Semantic Clarity

Another significant issue with `null` is its lack of **semantic clarity**. Why is the value absent? Is it uninitialized? Does it represent a failure or an intentional omission? Without additional context, null is ambiguous and prone to misuse.

Consider the following code example

```java
public String getUserEmail(User user) {
    if (user != null && user.getEmail() != null) {
        return user.getEmail();
    }
    return null; // What does this null result mean? Is the user invalid? Or is there no email?
}
```

### The Functional Programming Approach

[Functional Programming](01-introduction-to-functional-programming.md) emphasizes **explicitness** and **immutability**. Instead of relying on ambiguous `null` values, functional paradigms favor constructs that clearly express intent.

As functional programming gained traction, languages like **Scala** introduced the concept of `Option` or `Maybe` types to explicitly model the presence or absence of a value. Inspired by these, [Java](02-functional-programming-in-java.md) introduced `Optional` in **Java 8** to provide a more expressive, safer, and less error-prone way to handle potentially absent values.

## `Optional` in Java

`Optional` is a container object used to represent the presence or absence of a value. Rather than returning `null` for missing values, you can return an `Optional` instance to indicate explicitly whether a value is present.

- **Explicitly represents absence of a value:** Avoids surprises from `null` values.
- **Fluent API:** facilitates [functional programming in Java](02-functional-programming-in-java.md).
- **Improved readability:** Clearly communicates when a value might be absent.

### How to Create an `Optional`

Using `of`, `ofNullable` and `empty`

```java
// Creates an `Optional` for a non-null value.
Optional<String> nonNullValue = Optional.of("Hello, Optional!");
// Creates an `Optional` that allows `null` values.
Optional<String> nullableValue = Optional.ofNullable(null);
// Represents an empty `Optional`.
Optional<String> emptyOptional = Optional.empty();
```

### How to Provide Default Values

Use `orElse` to supply a default value when the `Optional` is empty.

```java
assertEquals("Value Exists", nonEmptyOptional.orElse("Default Name"));
assertEquals("Default Name", emptyOptional.orElse("Default Name"));
```

### How to Provide Values from Alternative Supplier

Use `or` when you have an alternative source for an `Optional` value.

```java
Function<Integer, Optional<String>> findInDefaultDataSource = (id) ->
        id == 1 ? Optional.of("Default Value") : Optional.empty();
Supplier<Optional<String>> findInAlternativeDataSource = () -> Optional.of("Alternative Value");

assertEquals("Default Value", findInDefaultDataSource.apply(1).or(findInAlternativeDataSource).get());
assertEquals("Alternative Value", findInDefaultDataSource.apply(2).or(findInAlternativeDataSource).get());
```

### How to Trigger an Error when Empty

Use `orElseThrow` when the absence of a value should be treated as an error.

```java
assertEquals("Value Exists", nonEmptyOptional.orElseThrow());
assertThrows(NoSuchElementException.class, emptyOptional::orElseThrow);
assertThrows(IllegalArgumentException.class,
                () -> emptyOptional.orElseThrow(() -> new IllegalArgumentException("Custom exception")));
```

### How to Do Lazy Evaluation

Use `orElseGet` when computing a fallback value involves a costly operation or side effects.

```java
@Test
public void testLazyEvaluation() {
    Optional<String> emptyOptional = Optional.empty();
    Optional<String> nonEmptyOptional = Optional.of("Value Exists");

    // orElseGet: Use when computing a fallback value involves a costly operation or side effects.
    assertEquals("Value Exists", nonEmptyOptional.orElseGet(() -> "Fallback Value"));
    // Demonstrating lazy evaluation
    AtomicBoolean supplierCalled = new AtomicBoolean(false);
    String result = emptyOptional.orElseGet(() -> {
        supplierCalled.set(true);
        return "Computed Value";
    });
    assertTrue(supplierCalled.get());
    assertEquals("Computed Value", result);
}
```

### How to Avoid NullPointerException

Instead of returning `null` and relying on the caller to handle it, return an `Optional`.

```java
@Test
public void testAvoidNullPointerException() {
    Function<Integer, Optional<String>> findUserById = (id) -> {
        if (id == 1) {
            return Optional.of("John Doe");
        }
        return Optional.empty();
    };

    assertTrue(findUserById.apply(1).isPresent());
    assertTrue(findUserById.apply(2).isEmpty());

    assertEquals("John Doe", findUserById.apply(1).orElse("User not found"));
    assertEquals("User not found", findUserById.apply(2).orElse("User not found"));

    findUserById.apply(1).ifPresent(
            name -> System.out.println("User found: " + name)
    );
    findUserById.apply(1).ifPresentOrElse(
            name -> System.out.println("User found: " + name),
            () -> System.out.println("User not found")
    );
}
```

### How to Transform Data Types

Use `map` to apply a function if a value is present, without needing null checks, to transform the data type.

```java
@Test
public void testTransformDataTypes() {
    Optional<String> emptyOptional = Optional.empty();
    Optional<String> nonEmptyOptional = Optional.of("Value Exists");

    assertEquals(0, emptyOptional.map(String::length).orElse(0));
    assertEquals(12, nonEmptyOptional.map(String::length).orElse(0));
}
```

### How to Transform Values

Use `map` to apply a function if a value is present, without needing null checks, to transform the data type.

Use `flatmap` when the transformation itself returns an `Optional`: it avoids nested `Optional<Optional<U>>` by "flattening" the result into a single `Optional`.

```java
@Test
public void testTransformValues() {
    Optional<String> city = Optional.of("New York");
    Optional<String> uppercaseCity = city
            .flatMap(c -> Optional.of(c.toUpperCase()));
    assertEquals("NEW YORK", uppercaseCity.orElse("Unknown"));
}
```

### How to Filter Values

Filter an `Optional` based on a condition.

```java
@Test
public void testFilterConditions() {
    Predicate<String> isNewCity = name -> name.startsWith("New");

    assertEquals("New York", Optional.of("New York")
            .filter(isNewCity)
            .orElse("new city not found"));
    assertEquals("new city not found", Optional.of("London")
            .filter(isNewCity)
            .orElse("new city not found"));
}
```

## When to Use `Optional`

### Good Use Cases

- **Method Return Types:** Indicate absence of a value.
- **Stream Pipelines:** Simplify handling of optional values.

### Avoid Misuse

- Do not use `Optional` for fields in objects.
- Do not serialize `Optional`.
- Avoid overuse; for simple scenarios, `null` may suffice.
- `Optional` introduces an additional object, which can affect performance in tight loops or memory-sensitive applications. For such cases, using null with disciplined checks may be more efficient.
- Passing `Optional` as a parameter can lead to misuse and confusion. Instead, provide overloaded methods or clearly documented APIs.

## Further Reading

1. [Functional Programming in Java](02-functional-programming-in-java.md)
2. [Lambda Expressions in Java](05-java-lambda-expressions.md)
3. [Functional Interfaces in Java](06-java-functional-interfaces.md)

## Code examples

- [OptionalTest](../src/test/java/es/htic/kata/java_functional_programming/OptionalTest.java)
