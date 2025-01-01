# Functional Programming in Java

**[Functional Programming](01-introduction-to-functional-programming.md)** is a programming paradigm that decomposes a problem into a set of Functions, focusing on immutability, pure functions, and declarative logic.

## Why Java for Functional Programming?

[Java](https://en.wikipedia.org/wiki/Java_(programming_language)) is a high-level Object-Oriented Programming language. Unlike purely functional languages such as Haskell or JVM-based functional languages like Scala, Kotlin, and Clojure, Java does not enforce functional programming principles, such as immutability or the use of pure functions. Hence, you need to rely heavily on best practices, manual code inspection and SAC (Static Code Analysis) tools such as SonarQube if you want to use Functional Programming in Java.

Java has been introducing Functional Programming features since Java 8 released in 2014, but still does not fully enforces Functional Programming. So why to chose Java for Functional Programing when there has been for a long time several other languages that enforces Functional Programming like Haskell? Or even other JVM languages that supports Functional Programming such as Kotlin, Scala or Clojure?

Main reason could be an easier learning curve for a more gradual adoption of the Functional Programming principles.

But also, Java has already a large community and tools while also performs really good in high-performance applications.

## Java support for Functional Programming

Since Java 8 (2014), the language has introduced features that align with functional programming paradigms.

- Functions become first-class citizens.
- Lambda expressions is a concise function definition.
- Functional Interfaces with a Single Abstract Method used as types for lambda expressions
- Streams is a declarative data processing pipelines.
- CompletableFuture allows functional-style async programming.

More minor enhancements have been added.

- Java 9 (2017) Introduction of `Optional`
- Java 9 (2017) Immutable Data Structures: Factory methods like `List.of`, `Set.of`, and `Map.of` allow concise creation of immutable collections.
- Java 11 (2018) `Optional` enhancements like `isEmpty` and `isPresent`.
- Java 12 (2019) introduces `Collectors`.
- Java 14 (2020) introduces switch expressions.

## `final` is NOT immutable in Java

Java provides a `final` keyword since first release. Declaring a variable as `final` means the reference cannot point to a different object after initialization. However, the object itself can still be mutable unless explicitly designed to be immutable.

A final primitive type is de facto immutable since the Java compiler will not allow its value to change.

```java
@Test
public void testFinalPrimitiveTypeIsImmutable() {
    final int primitiveNumber = 5;
    //Compiler does not allow to assign a new value
    //primitiveNumber++; //Cannot assign a value to final variable 'number'
    //primitiveNumber = 6; //Cannot assign a value to final variable 'number'
}
```

Same thing happens to an Integer, since once its value is set into a final variable, there is no way to change it.

```java
@Test
public void testFinalIntegerIsImmutable() {
    final Integer number = new Integer(5);
    //Compiler does not allow to assign a new value
    //number++; //Cannot assign a value to final variable 'number'
    //number = new Integer(6); //Cannot assign a value to final variable 'number'
}
```

Yet, a final Collection is NOT immutable by default in Java. 

```java
@Test
public void testFinalCollectionIsMutable() {
    //given a final collection
    final Collection<Integer> list = new ArrayList<>();
    //when a new element is added
    list.add(new Integer(1));
    //then collection size is increased
    assertEquals(1, list.size());
}
```

This was not changed in Java 8 when introducing Functional Programming, making this **the most common bug root causes when using Functional Programming in Java**.

```java
@Test
public void testFinalCollectionIsMutable() {
    //given a final collection
    final Collection<Integer> integersFrom1To100 = IntStream.rangeClosed(1, 100) // Generate numbers from 1 to 100
            .boxed()                                            // Convert int to Integer
            .collect(Collectors.toList());                      // Collect to a List
    assertEquals(100, integersFrom1To100.size());

    //when a new element is added
    integersFrom1To100.add(101);

    //then collection size is increased
    assertEquals(101, integersFrom1To100.size());
}
```

Yes, it is possible to make collections immutable in Java.

```java
@Test
public void testCollectionOfIsImmutableFromJava9() {
    assertThrows(UnsupportedOperationException.class, () -> List.of(1,2,3).add(4));
    assertThrows(UnsupportedOperationException.class, () -> Set.of(1,2,3).add(4));
}
@Test
public void testCollectionOfIsImmutableFromJava10() {
    //given a final collection
    Collection<Integer> integersFrom1To100 = IntStream.rangeClosed(1, 100)  // Generate numbers from 1 to 100
            .boxed()                                                        // Convert int to Integer
            .collect(Collectors.toUnmodifiableList());                      // Collect to a Immutable List

    //when a new element is added
    assertThrows(UnsupportedOperationException.class, () -> integersFrom1To100.add(101));
}
```
