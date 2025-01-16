# Functional Interfaces in Java

[Functional Programming](https://www.linkedin.com/pulse/introduction-functional-programming-jer%25C3%25B3nimo-calvo-s%25C3%25A1nchez-p2s8f) is a programming paradigm that decomposes a problem into a set of **Functions**. 

Java 8 introduced some major features to support [Functional Programming in Java](https://www.linkedin.com/pulse/functional-programming-java-jer%25C3%25B3nimo-calvo-s%25C3%25A1nchez-ybvdf) allowing [Data Immutability](https://www.linkedin.com/pulse/functional-programming-java-jer%25C3%25B3nimo-calvo-s%25C3%25A1nchez-ybvdf) and enabling [Functions as First-class citizens](https://www.linkedin.com/pulse/functions-first-class-citizens-java-jer%25C3%25B3nimo-calvo-s%25C3%25A1nchez-zsskf), introducing a new syntax, [lambda expressions](https://www.linkedin.com/pulse/lambda-expressions-java-jer%25C3%25B3nimo-calvo-s%25C3%25A1nchez-zxyif), to make Functions easier to write and read.

Also, **Functional Interfaces** were introduced to provide meaningful semantics to functions.

## What are Functional Interfaces

A **Functional Interface** in Java is an Interface with a Single Abstract Method (SAM). Java ensures the integrity of these interfaces at the compiler level with a single-method rule.

They are designed to work seamlessly with **lambda expressions and method references**, providing Provide **semantic clarity** by explicitly defining the intent of a function.

## Custom Functional Interfaces

We can define our own Custom Functional Interfaces annotating our interface with `@FunctionalInterface`. This enables the compiler to enforce SAM rule so that we provide one and only one Abstract Method to be implemented.

Let's see an example of a custom functional interface

```java
@FunctionalInterface
interface Greeting {
    void sayHello(String name);
}
```

This functional interface can be implemented quite easily using a lambda expression:

```java
Greeting greet = name -> System.out.println("Hello, " + name);
```

## Java native Functional Interfaces

Java provides several built-in functional interfaces in the `java.util.function` package for common use cases in Functional Programming to best categorize and reuse functions when decomposing problems:

| Interface              | Abstract Method       | Usage                                                              |
|------------------------|-----------------------|--------------------------------------------------------------------|
| `Predicate<T>`         | `boolean test(T t)`   | Represents a Condition                                             |
| `Consumer<T>`          | `void accept(T t)`    | Operation that takes a single argument and produces no result      |
| `Supplier<T, R>`       | `T get()`             | Represents a supplier of a result that takes no inputs             |
| `Function<T, R>`       | `R apply(T t)`        | Function that takes one argument and produces a result             |
| `UnaryOperator<T>`     | `T apply(T t)`        | Function where the input and the output are of the same type       |
| `BiFunction<T, U, R>`  | `R apply(T t, U u)`   | Function that takes two arguments and produces a result.           |
| `BinaryOperator<T>`    | `T apply(T t1, T t2)` | Function with two inputs of the same type, returning the same type |

### Predicates

`Predicate<T>` represents a Condition.

They are mainly used for data filtering and segmentation, which can be handy in data intelligence to identify patterns (risk analysis, fraud detection).

```java
    @Test
    public void testPredicates() {
        Predicate<String> isLongString = s -> s.length() > 5;
        Predicate<String> startsWithF = s -> s.startsWith("f") || s.startsWith("F");

        assertFalse(isLongString.test("hello"));
        assertTrue(isLongString.test("functional"));
        assertTrue(startsWithF.test("functional"));
        // Predicates can be easily chained
        assertTrue((isLongString.and(startsWithF)).test("functional"));
    }
```

### Consumer

A `Consumer<T>` is an operation that accepts one type `T` of data and produces no result.

This is a good way to isolate side effects, and can be used for persisting data, auditing, metrics collection, sending notifications...

```java
    @Test
    public void testConsumers() {
        Consumer<String> printMessage = s -> System.out.println("Logging: " + s);
        printMessage.accept("Example log message"); // Output: "Logging: Example log message"
    }
```

### Supplier

A `Supplier<T>` is a function that takes no argument and produces a result of type `T`.

They are mainly used to generate data for testing, configuration, default values...

```java
    @Test
    public void testSuppliers() {
        Supplier<Double> randomValue = () -> Math.random();
        // Supplier<User> getRandomUser = User::random; // Example of a class User that would have one method random to generate a random user to be used in testing.
        System.out.println(randomValue.get());
    }
```

### Function<T, R>

`Function<T, R>` interfaces are Functions that take a type `T` as input parameter and returns type `R` as result.

They are typically used for transforming data from type `T` to type `R`, which can be handy for transforming data form one domain to another, like generating reports, or currency conversion, processing natural language...

```java
    @Test
    public void testFunctions() {
        Function<Integer, String> intToString = i -> "Number: " + i;
        assertEquals("Number: 42", intToString.apply(42));
    }
```

### Unitary Operator

`UnaryOperator<T>` is a Function where the input and the output are of the same type `T`. It is actually a specialization of `Function<T,T>`.

Typically used to modify data in batches, such as updating statuses when an order has been shipped, apply taxes, data normalization.

```java
prices.stream()
                .map(price -> price * 0.9)
                .collect(Collectors.toList());
```

### BiFunction

`BiFunction<T, U, R>` is a Function that takes two arguments and produces a result.

They are mainly used to combine data for conflict resolution, data enrichment or generating financial summaries.

```java
@Test
public void testCompareNames() {
    // BiFunction<String, String, Boolean> twoUsersHaveSameName = String::equalsIgnoreCase;
    BiFunction<String, String, Boolean> twoUsersHaveSameName = (firstName, secondName) -> firstName.equalsIgnoreCase(secondName);

    assertFalse(twoUsersHaveSameName.apply("John", "Peter"));
    assertTrue(twoUsersHaveSameName.apply("John", "joHN"));
}
```

### BinaryOperator

`BinaryOperator<T>` is a specialization of `BiFunction<T, T, T>`.

```java
@Test
public void testComposeFullNames() {
    BinaryOperator<String> fullNameUsingBinaryOperator = (firstName, lastName) -> lastName + ", " + firstName;
    BiFunction<String, String, String> fullNameUsingBiFunction = (firstName, lastName) -> lastName + ", " + firstName;

    assertEquals("Doe, John", fullNameUsingBinaryOperator.apply("John", "Doe"));
    assertEquals("Doe, John", fullNameUsingBiFunction.apply("John", "Doe"));
}
```

## More native Functional Interfaces

There are other well known Interfaces in Java that has benefit from these changes, like
- `Runnable` since Java 1, represents a task to be executed on a separate thread.

```java
Runnable task = () -> System.out.println("Task is running...");
new Thread(task).start();
```

- `Comparator` since Java 2, for comparing and sorting objects. 

```java
Comparator<String> byLength = (s1, s2) -> Integer.compare(s1.length(), s2.length());
List<String> words = List.of("apple", "banana", "cherry");
words.sort(byLength);
```

- `Callable` since Java 5, is similar to `Runnable` but returns a value and can throw exceptions

```java
Callable<String> task = () -> "Task completed!";
ExecutorService executor = Executors.newSingleThreadExecutor();

try {
    Future<String> result = executor.submit(task);
    System.out.println(result.get());
} catch (Exception e) {
    e.printStackTrace();
} finally {
    executor.shutdown();
}
```

- `ActionListener` since Java 1, to handle events

```java
button.addActionListener(e -> System.out.println("Button clicked!"));
```

## Code examples

- [FunctionalInterfacesExamples](../src/main/java/es/htic/kata/java_functional_programming/FunctionalInterfacesExamples.java)
- [FunctionalInterfacesExamplesTest](../src/test/java/es/htic/kata/java_functional_programming/FunctionalInterfacesExamplesTest.java)
