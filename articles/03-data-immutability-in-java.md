
# Data immutability in Java

**Data immutability** is one of the core principles of [Functional Programming](https://www.linkedin.com/pulse/introduction-functional-programming-jer%C3%B3nimo-calvo-s%C3%A1nchez-p2s8f): means that once data is created, it should not be modified nor updated, and instead new data should be created.

- Easier to test, more predictable and maintainable code
- Thread-safe operations
- Caching data
- The price to pay is probably an increase in memory usage and some operations can be less efficient.

This concept is not straight forward to implement in Java, since there is no concept of a `constant` like in other languages, but rather the concept of `final`.

Hence, developers must build immutability in their own code since there is no built-in mechanism provided in Java.

## `final` is NOT immutable in Java

Java provides a `final` keyword since its first release, that can be applied to a variable, a method or a class.

Declaring a variable as `final` means the reference cannot point to a different object after initialization. However, the object itself can still be mutable unless explicitly designed to be immutable.

This has been probably the most common source of bugs for developers when getting started in Functional Programming in Java.

### `final` primitive variable

A `final` primitive type is de facto immutable since the Java compiler will not allow its value to change.

```java
@Test
public void testFinalPrimitiveTypeIsImmutable() {
    final int primitiveNumber = 5;
    //Compiler does not allow to assign a new value
    //primitiveNumber++; //Cannot assign a value to final variable 'number'
    //primitiveNumber = 6; //Cannot assign a value to final variable 'number'
}
```

### `final` object variable

Same thing happens to an `Integer` or a `String`, once its value is set into a `final` variable, there is no way to change it.

```java
@Test
public void testFinalIntegerIsImmutable() {
    final Integer number = new Integer(5);
    //Compiler does not allow to assign a new value
    //number++; //Cannot assign a value to final variable 'number'
    //number = new Integer(6); //Cannot assign a value to final variable 'number'
}
```

Yet, the reason is NOT because `final` turns the `variable` into a `constant`, but because the object we are using (`Integer`, `String`...) **is designed to be immutable**.

Let's see another example with another native object, `Collection`, that is NOT immutable by default in Java.

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

This was not changed in Java 8 when introducing Functional Programming, making this **the most common bug root cause when using Functional Programming in Java**.

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

Yes, it is possible to make your own immutable collections in Java, like some third parties as `Guava` did.

Or we can use other Collections designed to be immutable.

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

Note that **Immutable views**, introduced in Java 2, do not allow to modify the data through the collection reference, but they do not protect the underlying collection from being altered somewhere else. 

```java
@Test
public void testImmutableView() {
    // Original mutable list
    List<Integer> originalList = new ArrayList<>(List.of(1, 2, 3));

    // Create an immutable view
    List<Integer> immutableView = Collections.unmodifiableList(originalList);

    // Modifications via the view are not allowed
    assertThrows(UnsupportedOperationException.class, () -> immutableView.add(4));

    // However, changes to the original list are reflected in the view
    originalList.add(4);
    assertEquals(List.of(1, 2, 3, 4), immutableView);
}
```

### `final` methods and classes

Methods and Classes can also be annotated as `final`, but this is not related to data immutability.

- A `final` method cannot be overwritten, hence allowing the compiler to apply optimizations since it is not going to be overwritten in execution time.
- A `final` class cannot be extended and making this type safety.

## How to make your object immutable in Java

Since there is no built-in mechanism in Java to make an object immutable, we need to design for it ourselves. Best practices are:

- Declare your class `final`: this ensures that it cannot be extended, preventing subclasses from altering its immutability.
- Encapsulation: make all your fields `private` and `final`
- Provide only getter methods, but do not implement a setter
- Do not use references to mutable data
  - Use a deep copy when reading external data
  - Return a copy of your data
- Use fully immutable fields when possible
- Factory pattern: make sure to control how your object is created.
- Review your public methods to enforce that no changes are done to your internal variables. 
- (Java 14+) Use `record` when possible, specially as DTO (Data Transfer Objects) or any class that primarily carries data without complex behavior.
- TEST for immutability

## Code examples

- [DataImmutabilityExamples](../src/main/java/es/htic/kata/java_functional_programming/DataImmutabilityExamples.java) class
- [DataImmutabilityTest](../src/test/java/es/htic/kata/java_functional_programming/DataImmutabilityTest.java) class